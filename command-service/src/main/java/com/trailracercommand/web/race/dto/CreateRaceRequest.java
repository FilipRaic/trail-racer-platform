package com.trailracercommand.web.race.dto;

import com.trailracer.events.RaceDistance;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateRaceRequest(

        @NotBlank
        String name,

        @NotNull
        @FutureOrPresent
        Instant startDateTimeUtc,

        @NotNull
        RaceDistance distance
) {
}
