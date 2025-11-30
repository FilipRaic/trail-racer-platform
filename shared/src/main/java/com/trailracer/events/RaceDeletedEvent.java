package com.trailracer.events;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RaceDeletedEvent(
        UUID id,
        Long version
) implements DomainEvent {

    @Override
    public String eventType() {
        return "RaceDeleted";
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
