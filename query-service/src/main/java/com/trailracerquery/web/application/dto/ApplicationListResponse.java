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
public class ApplicationListResponse {

    private UUID id;
    private String userFirstName;
    private String userLastName;
    private String raceName;
    private Instant raceStartDateTimeUtc;
    private Long version;
}
