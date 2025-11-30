package com.trailracercommand.shared.mail;

import com.trailracercommand.web.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MockMailServiceImpl implements EmailService {

    @Override
    public void sendResetPasswordEmail(User user, String token) {
        LOG.info("Sending MOCK reset password email to user with ID: {}", user.getId());
    }
}
