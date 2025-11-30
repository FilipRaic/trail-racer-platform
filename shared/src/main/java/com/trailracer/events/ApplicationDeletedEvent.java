package com.trailracer.events;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ApplicationDeletedEvent(
        UUID id,
        Long version
) implements DomainEvent {

    @Override
    public String eventType() {
        return "ApplicationDeleted";
    }

    @Override
    public String aggregateType() {
        return "Application";
    }

    @Override
    public String aggregateId() {
        return String.valueOf(id);
    }
}
