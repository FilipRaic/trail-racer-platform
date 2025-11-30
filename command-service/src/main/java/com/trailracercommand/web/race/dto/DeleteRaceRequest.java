package com.trailracercommand.web.race.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record DeleteRaceRequest(

        @NotNull
        UUID id,

        @NotNull
        @PositiveOrZero
        Long version
) {
}
