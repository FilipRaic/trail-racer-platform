package com.trailracercommand.shared.mail;

import com.trailracercommand.web.user.model.User;

public interface EmailService {

    void sendResetPasswordEmail(User user, String token);
}
