package com.trailracercommand.web.user.dto;

import com.trailracercommand.shared.exception.CustomException;
import jakarta.validation.constraints.*;
import lombok.Builder;

import static com.trailracercommand.shared.exception.ErrorMessage.PASSWORDS_DO_NOT_MATCH;

@Builder
public record RegisterRequest(

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotNull
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[!@#$%^&*]).*$",
                message = "Password must contain at least one number and one special character"
        )
        String password,

        @NotNull
        @NotBlank(message = "Confirm password is required")
        String confirmPassword
) {
    public RegisterRequest {
        if (!password.equals(confirmPassword)) {
            throw new CustomException(PASSWORDS_DO_NOT_MATCH);
        }
    }
}
