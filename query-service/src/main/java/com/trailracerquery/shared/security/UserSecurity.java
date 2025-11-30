package com.trailracerquery.shared.security;

import com.trailracerquery.shared.exception.CustomException;
import com.trailracerquery.web.user.model.User;
import com.trailracerquery.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.trailracerquery.shared.exception.ErrorMessage.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class UserSecurity {

    private final UserRepository userRepository;

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user.getId();
        } else if (principal instanceof org.springframework.security.core.userdetails.User userDetails) {
            String email = userDetails.getUsername();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

            return user.getId();
        }

        throw new CustomException(USER_NOT_FOUND);
    }

    public User getCurrentUser() {
        Long currentUserId = getCurrentUserId();
        return userRepository.findById(currentUserId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
