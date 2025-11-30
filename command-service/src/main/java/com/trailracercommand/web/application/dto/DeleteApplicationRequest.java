package com.trailracercommand.web.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record DeleteApplicationRequest(

        @NotNull
        UUID id,

        @NotNull
        @PositiveOrZero
        Long version
) {
}
