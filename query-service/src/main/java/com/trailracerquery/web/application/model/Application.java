package com.trailracerquery.web.application.model;

import com.trailracerquery.web.race.model.Race;
import com.trailracerquery.web.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
                @Index(name = "ix_application_user", columnList = "user_id"),
                @Index(name = "ix_application_race", columnList = "race_id")
        }
)
public class Application {

    @Id
    private UUID id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_application_user"))
    private User user;

    @Column
    @NotNull
    private String club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", nullable = false, foreignKey = @ForeignKey(name = "fk_application_race"))
    private Race race;

    @Version
    @Column(nullable = false)
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Application that))
            return false;

        if (Objects.nonNull(id) && Objects.nonNull(that.getId()))
            return id.equals(that.getId());

        return Objects.equals(user, that.user) &&
                Objects.equals(club, that.club) &&
                Objects.equals(race, that.race);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, club, race);
    }
}
