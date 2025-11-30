package com.trailracerquery.web.application.service;

import com.trailracer.events.ApplicationCreatedEvent;
import com.trailracer.events.ApplicationDeletedEvent;
import com.trailracer.events.DomainEvent;
import com.trailracerquery.shared.exception.CustomException;
import com.trailracerquery.web.application.model.Application;
import com.trailracerquery.web.application.repository.ApplicationRepository;
import com.trailracerquery.web.race.model.Race;
import com.trailracerquery.web.race.repository.RaceRepository;
import com.trailracerquery.web.user.model.User;
import com.trailracerquery.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.trailracerquery.shared.exception.ErrorMessage.RACE_NOT_FOUND;
import static com.trailracerquery.shared.exception.ErrorMessage.USER_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationEventHandler {

    private final RaceRepository raceRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    @KafkaListener(
            topics = "trail-racer-application-events",
            groupId = "query-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(ConsumerRecord<String, Object> consumerRecord) {
        DomainEvent event = (DomainEvent) consumerRecord.value();

        LOG.info("Received event: {} | Offset: {} | Partition: {}",
                event.getClass().getSimpleName(), consumerRecord.offset(), consumerRecord.partition());

        switch (event) {
            case ApplicationCreatedEvent e -> handleApplicationCreatedEvent(e);
            case ApplicationDeletedEvent e -> handleApplicationDeletedEvent(e);
            default -> LOG.warn("Unknown or null event received: {}", event);
        }
    }

    private void handleApplicationCreatedEvent(ApplicationCreatedEvent event) {
        LOG.info("ApplicationCreatedEvent: {}", event.id());

        applicationRepository.findById(event.id())
                .ifPresentOrElse(
                        existing -> LOG.info("Application {} already exists (v{}), skipping creation",
                                event.id(), existing.getVersion()),
                        () -> {
                            User user = userRepository.findById(event.userId())
                                    .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
                            Race race = raceRepository.findById(event.raceId())
                                    .orElseThrow(() -> new CustomException(RACE_NOT_FOUND));

                            applicationRepository.save(Application.builder()
                                    .id(event.id())
                                    .user(user)
                                    .club(event.club())
                                    .race(race)
                                    .build());
                        }
                );
    }

    private void handleApplicationDeletedEvent(ApplicationDeletedEvent event) {
        LOG.info("ApplicationDeletedEvent: {}", event.id());

        applicationRepository.findById(event.id()).ifPresent(application -> {
            if (!Objects.equals(application.getVersion(), event.version())) {
                LOG.info("Ignoring older/out-of-order delete v{} (current v{}) for aggregate={}",
                        event.version(), application.getVersion(), event.id());
                return;
            }

            applicationRepository.deleteById(event.id());
        });
    }
}
