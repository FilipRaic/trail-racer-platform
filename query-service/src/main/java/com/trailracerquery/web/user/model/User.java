package com.trailracerquery.web.user.model;

import com.trailracerquery.web.user.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "app_user",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"}, name = "uk_user_email"),
        indexes = {@Index(columnList = "email", name = "ix_users_email")}
)
public class User {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_generator")
    @SequenceGenerator(name = "app_user_generator", sequenceName = "seq_app_user_generator")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @NotNull
    @ToString.Exclude
    @Column(nullable = false)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User that))
            return false;

        if (Objects.nonNull(id) && Objects.nonNull(that.getId()))
            return id.equals(that.getId());

        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, dateOfBirth, role);
    }
}
