package com.trailracercommand.web.user.service;

import com.trailracercommand.shared.exception.CustomException;
import com.trailracercommand.shared.mail.EmailService;
import com.trailracercommand.shared.security.JwtService;
import com.trailracercommand.shared.security.RefreshTokenService;
import com.trailracercommand.web.user.dto.*;
import com.trailracercommand.web.user.enums.Role;
import com.trailracercommand.web.user.model.RefreshToken;
import com.trailracercommand.web.user.model.User;
import com.trailracercommand.web.user.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.trailracercommand.shared.exception.ErrorMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(@NonNull RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        }

        User user = userRepository.save(
                User.builder()
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .role(Role.APPLICANT)
                        .build());

        return generateAuthResponse(user, request.password());
    }

    @Transactional
    public AuthResponse login(@NonNull LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return generateAuthResponse(user, request.password());
    }

    @Transactional
    public AuthResponse refreshAccessToken(Long userId) {
        return refreshTokenService.findByUserId(userId)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user ->
                        AuthResponse.builder()
                                .accessToken(jwtService.generateToken(user))
                                .build())
                .orElseThrow(() -> new CustomException(REFRESH_TOKEN_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public void sendResetPasswordEmail(ResetPasswordEmailRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        String token = jwtService.generateResetPasswordToken(user);

        emailService.sendResetPasswordEmail(user, token);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        user.updatePassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);
    }

    private AuthResponse generateAuthResponse(User user, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            password
                    )
            );
        } catch (Exception e) {
            LOG.error("Error on login", e);
            throw new CustomException(INVALID_CREDENTIALS);
        }

        String jwtToken = jwtService.generateToken(user);
        refreshTokenService.createRefreshToken(user.getId());

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
