package com.trailracerquery;

import com.trailracerquery.web.user.enums.Role;
import com.trailracerquery.web.user.model.User;
import com.trailracerquery.web.user.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile({"local", "dev"})
public class DataLoadService implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(@NonNull ApplicationArguments args) {
        if (userRepository.count() == 0) {
            generateUsers();
            LOG.info("Added sample user");
        }
    }

    private void generateUsers() {
        userRepository.save(User.builder()
                .email("applicant@test.com")
                .firstName("Test")
                .lastName("Applicant")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .role(Role.APPLICANT)
                .password(passwordEncoder.encode("TestApplicant123!"))
                .build());

        userRepository.save(User.builder()
                .email("admin@test.com")
                .firstName("Test")
                .lastName("Admin")
                .dateOfBirth(LocalDate.of(1995, 1, 1))
                .role(Role.ADMIN)
                .password(passwordEncoder.encode("TestAdmin123!"))
                .build());
    }
}
