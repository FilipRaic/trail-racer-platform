package com.trailracercommand.web.race.dto;

import com.trailracer.events.RaceDistance;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Instant;
import java.util.UUID;

public record UpdateRaceRequest(

        @NotNull
        UUID id,

        String name,

        Instant startDateTimeUtc,

        RaceDistance distance,

        @NotNull
        @PositiveOrZero
        Long version
) {
}
