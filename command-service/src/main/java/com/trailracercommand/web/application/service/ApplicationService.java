package com.trailracercommand.web.application.service;

import com.trailracer.events.ApplicationCreatedEvent;
import com.trailracer.events.ApplicationDeletedEvent;
import com.trailracercommand.shared.events.DomainEventPublisher;
import com.trailracercommand.web.application.dto.CreateApplicationRequest;
import com.trailracercommand.web.application.dto.DeleteApplicationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final DomainEventPublisher eventPublisher;

    @Transactional
    public UUID create(CreateApplicationRequest request) {
        UUID id = UUID.randomUUID();

        eventPublisher.publish(ApplicationCreatedEvent.builder()
                .id(id)
                .userId(request.userId())
                .club(request.club())
                .raceId(request.raceId())
                .build());

        return id;
    }

    @Transactional
    public void delete(DeleteApplicationRequest request) {
        eventPublisher.publish(ApplicationDeletedEvent.builder()
                .id(request.id())
                .version(request.version())
                .build());
    }
}
