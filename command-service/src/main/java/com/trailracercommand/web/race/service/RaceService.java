package com.trailracercommand.web.race.service;

import com.trailracer.events.RaceCreatedEvent;
import com.trailracer.events.RaceDeletedEvent;
import com.trailracer.events.RaceUpdatedEvent;
import com.trailracercommand.shared.events.DomainEventPublisher;
import com.trailracercommand.web.race.dto.CreateRaceRequest;
import com.trailracercommand.web.race.dto.DeleteRaceRequest;
import com.trailracercommand.web.race.dto.UpdateRaceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RaceService {

    private final DomainEventPublisher eventPublisher;

    @Transactional
    public UUID create(CreateRaceRequest request) {
        UUID id = UUID.randomUUID();

        eventPublisher.publish(RaceCreatedEvent.builder()
                .id(id)
                .name(request.name())
                .startDateTimeUtc(request.startDateTimeUtc())
                .distance(request.distance())
                .build());

        return id;
    }

    @Transactional
    public void update(UpdateRaceRequest request) {
        eventPublisher.publish(RaceUpdatedEvent.builder()
                .id(request.id())
                .name(request.name())
                .startDateTimeUtc(request.startDateTimeUtc())
                .distance(request.distance())
                .version(request.version())
                .build());
    }

    @Transactional
    public void delete(DeleteRaceRequest request) {
        eventPublisher.publish(RaceDeletedEvent.builder()
                .id(request.id())
                .version(request.version())
                .build());
    }
}
