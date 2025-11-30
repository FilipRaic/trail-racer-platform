package com.trailracercommand.web.user.repository;

import com.trailracercommand.web.user.model.RefreshToken;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<@NonNull RefreshToken, @NonNull Long> {

    Optional<RefreshToken> findByUserId(Long userId);

    @Modifying
    void deleteAllByUserId(Long userId);
}
