package com.trailracercommand.web.user.dto;

import com.trailracercommand.shared.exception.CustomException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.trailracercommand.shared.exception.ErrorMessage.PASSWORDS_DO_NOT_MATCH;

public record ResetPasswordRequest(

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
    public ResetPasswordRequest {
        if (!password.equals(confirmPassword)) {
            throw new CustomException(PASSWORDS_DO_NOT_MATCH);
        }
    }
}
