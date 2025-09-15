package com.rohitvpatil0810.v2data.modules.auth.service;

import com.rohitvpatil0810.v2data.common.email.EmailService;
import com.rohitvpatil0810.v2data.common.security.TokenGenerator;
import com.rohitvpatil0810.v2data.common.security.TokenHasher;
import com.rohitvpatil0810.v2data.modules.auth.entity.VerificationToken;
import com.rohitvpatil0810.v2data.modules.auth.repository.VerificationTokenRepository;
import com.rohitvpatil0810.v2data.modules.users.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    final private VerificationTokenRepository verificationTokenRepository;
    final private EmailService emailService;

    @Value("${app.base-url}")
    private String baseUrl;

    public void sendVerificationEmail(User user) {
        String token = createTokenFromUser(user);
        log.debug("Token = {}", token);
        String verificationLink = baseUrl + "/auth/verify?token=" + token;
        String subject = "Verify your email";
        String body = "<p>Click the link to verify your email:</p>" +
                "<a href=\"" + verificationLink + "\">Verify Email</a>";
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    private String createTokenFromUser(User user) {
        String rawToken = TokenGenerator.generateRawToken();
        String hashedToken = TokenHasher.hashToken(rawToken);

        VerificationToken verificationToken = VerificationToken.builder()
                .user(user)
                .hashedToken(hashedToken)
                .expiryTime(LocalDateTime.now().plusMinutes(15))
                .build();

        log.debug("Hashed Token = {}", hashedToken);
        verificationTokenRepository.save(verificationToken);

        return rawToken;
    }
}
