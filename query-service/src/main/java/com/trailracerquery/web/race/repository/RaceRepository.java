package com.trailracerquery.web.race.repository;

import com.trailracerquery.web.race.model.Race;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RaceRepository extends JpaRepository<@NonNull Race, @NonNull UUID> {

    boolean existsByIdAndVersionGreaterThanEqual(UUID id, Long version);
}
