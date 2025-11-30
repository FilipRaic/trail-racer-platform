package com.trailracer.events;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record RaceUpdatedEvent(
        UUID id,
        String name,
        Instant startDateTimeUtc,
        RaceDistance distance,
        Long version
) implements DomainEvent {

    @Override
    public String eventType() {
        return "RaceUpdated";
    }

    @Override
    public String aggregateType() {
        return "Race";
    }

    @Override
    public String aggregateId() {
        return String.valueOf(id);
    }
}
