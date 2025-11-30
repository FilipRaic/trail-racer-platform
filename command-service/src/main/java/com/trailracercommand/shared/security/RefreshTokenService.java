package com.trailracercommand.shared.security;

import com.trailracercommand.shared.exception.CustomException;
import com.trailracercommand.web.user.model.RefreshToken;
import com.trailracercommand.web.user.model.User;
import com.trailracercommand.web.user.repository.RefreshTokenRepository;
import com.trailracercommand.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.trailracercommand.shared.exception.ErrorMessage.REFRESH_TOKEN_EXPIRED;
import static com.trailracercommand.shared.exception.ErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${application.security.jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    @Transactional
    public void createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration))
                .build();

        refreshTokenRepository.deleteAllByUserId(userId);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new CustomException(REFRESH_TOKEN_EXPIRED);
        }

        return token;
    }
}
