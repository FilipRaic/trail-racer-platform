package com.trailracerquery.web.race.dto;

import com.trailracer.events.RaceDistance;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RaceResponse {

    private UUID id;
    private String name;
    private Instant startDateTimeUtc;
    private RaceDistance distance;
    private Long version;
}
