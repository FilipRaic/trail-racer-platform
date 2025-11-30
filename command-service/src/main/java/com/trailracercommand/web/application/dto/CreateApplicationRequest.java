package com.trailracercommand.web.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;

import java.util.UUID;

public record CreateApplicationRequest(

        @NotNull
        @Positive
        Long userId,

        String club,

        @NotNull
        UUID raceId,

        @NonNull
        @PositiveOrZero
        Long version
) {
}
