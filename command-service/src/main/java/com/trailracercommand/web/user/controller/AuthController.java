package com.trailracercommand.web.user.controller;

import com.trailracercommand.shared.security.UserSecurity;
import com.trailracercommand.web.user.dto.*;
import com.trailracercommand.web.user.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserSecurity userSecurity;

    @PostMapping("/register")
    public ResponseEntity<@NonNull AuthResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<@NonNull AuthResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PutMapping("/refresh-token")
    @PreAuthorize("@userSecurity.isCurrentUser()")
    public ResponseEntity<@NonNull AuthResponse> refreshToken() {
        Long userId = userSecurity.getCurrentUserId();

        return ResponseEntity.ok(userService.refreshAccessToken(userId));
    }

    @PostMapping("/forgot-password/send")
    public ResponseEntity<@NonNull Void> sendResetPasswordEmail(@Valid @RequestBody ResetPasswordEmailRequest request) {
        userService.sendResetPasswordEmail(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    @PreAuthorize("@userSecurity.isCurrentUser()")
    public ResponseEntity<@NonNull Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        String email = userSecurity.getCurrentUserEmail();
        userService.resetPassword(request, email);

        return ResponseEntity.ok().build();
    }
}
