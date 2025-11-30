package com.trailracercommand.web.user.dto;

import lombok.Builder;

@Builder
public record AuthResponse(String accessToken) {
}
