package com.trailracerquery.web.application.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private UUID raceId;
    private String raceName;
    private Instant raceStartDateTimeUtc;
    private String club;
    private Long version;
}
