package com.trailracerquery.web.race.model;

import com.trailracer.events.RaceDistance;
import com.trailracer.events.RaceUpdatedEvent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(
        indexes = {
                @Index(name = "ix_race_name", columnList = "name"),
                @Index(name = "ix_race_start_date_time_utc", columnList = "start_date_time_utc")
        }
)
public class Race {

    @Id
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Instant startDateTimeUtc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RaceDistance distance;

    @Version
    @Column(nullable = false)
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Race that))
            return false;

        if (Objects.nonNull(id) && Objects.nonNull(that.getId()))
            return id.equals(that.getId());

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDateTimeUtc, distance);
    }

    public void updateRace(RaceUpdatedEvent raceUpdatedEvent) {
        if (Objects.nonNull(raceUpdatedEvent.name())) {
            this.name = raceUpdatedEvent.name();
        }

        if (Objects.nonNull(raceUpdatedEvent.startDateTimeUtc())) {
            this.startDateTimeUtc = raceUpdatedEvent.startDateTimeUtc();
        }

        if (Objects.nonNull(raceUpdatedEvent.distance())) {
            this.distance = raceUpdatedEvent.distance();
        }
    }
}
