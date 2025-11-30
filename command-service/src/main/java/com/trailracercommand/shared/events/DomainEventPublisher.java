package com.trailracercommand.shared.events;

import com.trailracer.events.DomainEvent;

public interface DomainEventPublisher {

    void publish(DomainEvent event);
}
