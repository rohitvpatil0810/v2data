package com.rohitvpatil0810.v2data.modules.auth.service;

import com.rohitvpatil0810.v2data.common.api.exceptions.BadRequestException;
import com.rohitvpatil0810.v2data.common.email.EmailService;
import com.rohitvpatil0810.v2data.common.security.TokenGenerator;
import com.rohitvpatil0810.v2data.common.security.TokenHasher;
import com.rohitvpatil0810.v2data.modules.auth.entity.VerificationToken;
import com.rohitvpatil0810.v2data.modules.auth.repository.VerificationTokenRepository;
import com.rohitvpatil0810.v2data.modules.users.entity.User;
import com.rohitvpatil0810.v2data.modules.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    final private VerificationTokenRepository verificationTokenRepository;
    final private EmailService emailService;
    final private UserRepository userRepository;

    // TODO: in future get this value from properties
    private final long expirationTimeInSeconds = 15 * 60; // 15 minutes * 60 sec per minute

    @Value("${app.base-url}")
    private String baseUrl;

    public void sendVerificationEmail(User user) {
        String token = createTokenFromUser(user);
        log.debug("Token = {}", token);
        String verificationLink = this.baseUrl + "/auth/verify?token=" + token;
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
                .build();

        log.debug("Hashed Token = {}", hashedToken);
        verificationTokenRepository.save(verificationToken);

        return rawToken;
    }

    public void verifyToken(String rawToken) throws BadRequestException {
        try {
            String hashToken = TokenHasher.hashToken(rawToken);
            VerificationToken verificationToken = verificationTokenRepository.findByHashedToken(hashToken).orElseThrow();

            if (verificationToken.getCreatedAt().plusSeconds(expirationTimeInSeconds).isBefore(Instant.now()) || verificationToken.getUsed()) {
                throw new BadRequestException("Email verification link expired");
            }

            User user = verificationToken.getUser();
            user.setIsEmailVerified(true);
            userRepository.save(user);

            verificationToken.setUsed(true);
            verificationTokenRepository.save(verificationToken);
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Email verification failed");
        }
    }
}
