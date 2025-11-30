package com.trailracer.events;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ApplicationCreatedEvent(UUID id,
                                      Long userId,
                                      String club,
                                      UUID raceId
) implements DomainEvent {

    @Override
    public String eventType() {
        return "ApplicationCreated";
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
