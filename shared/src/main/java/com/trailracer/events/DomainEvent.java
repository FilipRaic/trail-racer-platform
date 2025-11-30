package com.trailracer.events;

import java.time.Instant;

public interface DomainEvent {

    String eventType();

    String aggregateType();

    String aggregateId();

    default Instant occurredAt() {
        return Instant.now();
    }
}
