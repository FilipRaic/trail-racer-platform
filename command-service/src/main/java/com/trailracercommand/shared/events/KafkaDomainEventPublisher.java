package com.trailracercommand.shared.events;

import com.trailracer.events.DomainEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaDomainEventPublisher implements DomainEventPublisher {

    private static final String RACE_TOPIC = "trail-racer-race-events";
    private static final String APPLICATION_TOPIC = "trail-racer-application-events";

    private final KafkaTemplate<@NonNull String, @NonNull Object> kafkaTemplate;

    @Override
    public void publish(DomainEvent event) {
        LOG.info("Publishing domain event: type={} aggregateType={} aggregateId={}",
                event.eventType(), event.aggregateType(), event.aggregateId());

        String topic = event.aggregateType().equals("Race") ? RACE_TOPIC : APPLICATION_TOPIC;
        kafkaTemplate.send(topic, event.aggregateId(), event);
    }
}
