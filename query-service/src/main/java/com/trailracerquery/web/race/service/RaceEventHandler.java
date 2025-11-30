package com.trailracerquery.web.race.service;

import com.trailracer.events.DomainEvent;
import com.trailracer.events.RaceCreatedEvent;
import com.trailracer.events.RaceDeletedEvent;
import com.trailracer.events.RaceUpdatedEvent;
import com.trailracerquery.web.race.model.Race;
import com.trailracerquery.web.race.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class RaceEventHandler {

    private final RaceRepository raceRepository;

    @Transactional
    @KafkaListener(
            topics = "trail-racer-race-events",
            groupId = "query-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(ConsumerRecord<String, Object> consumerRecord) {
        DomainEvent event = (DomainEvent) consumerRecord.value();

        LOG.info("Received event: {} | Offset: {} | Partition: {}",
                event.getClass().getSimpleName(), consumerRecord.offset(), consumerRecord.partition());

        switch (event) {
            case RaceCreatedEvent e -> handleRaceCreatedEvent(e);
            case RaceUpdatedEvent e -> handleRaceUpdatedEvent(e);
            case RaceDeletedEvent e -> handleRaceDeletedEvent(e);
            default -> LOG.warn("Unknown or null event received: {}", event);
        }
    }

    private void handleRaceCreatedEvent(RaceCreatedEvent event) {
        LOG.info("RaceCreatedEvent: {}", event.id());

        raceRepository.findById(event.id())
                .ifPresentOrElse(
                        existing -> LOG.info("Race {} already exists (v{}), skipping creation",
                                event.id(), existing.getVersion()),
                        () -> raceRepository.save(Race.builder()
                                .id(event.id())
                                .name(event.name())
                                .startDateTimeUtc(event.startDateTimeUtc())
                                .distance(event.distance())
                                .build())
                );
    }

    private void handleRaceUpdatedEvent(RaceUpdatedEvent event) {
        LOG.info("RaceUpdatedEvent: {} (v{})", event.id(), event.version());

        raceRepository.findById(event.id()).ifPresent(race -> {
            if (!Objects.equals(race.getVersion(), event.version())) {
                LOG.info("Ignoring older/out-of-order update v{} (current v{}) for aggregate={}",
                        event.version(), race.getVersion(), event.id());
                return;
            }

            race.updateRace(event);
            raceRepository.save(race);
        });
    }

    private void handleRaceDeletedEvent(RaceDeletedEvent event) {
        LOG.info("RaceDeletedEvent: {}", event.id());

        raceRepository.findById(event.id()).ifPresent(race -> {
            if (!Objects.equals(race.getVersion(), event.version())) {
                LOG.info("Ignoring older/out-of-order delete v{} (current v{}) for aggregate={}",
                        event.version(), race.getVersion(), event.id());
                return;
            }

            raceRepository.deleteById(event.id());
        });
    }
}
