package com.rohitvpatil0810.v2data.modules.auth.service;

import com.rohitvpatil0810.v2data.common.security.TokenGenerator;
import com.rohitvpatil0810.v2data.common.security.TokenHasher;
import com.rohitvpatil0810.v2data.modules.auth.entity.VerificationToken;
import com.rohitvpatil0810.v2data.modules.auth.repository.VerificationTokenRepository;
import com.rohitvpatil0810.v2data.modules.users.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class EmailVerificationService {

    final private VerificationTokenRepository verificationTokenRepository;

    public EmailVerificationService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public void sendVerificationEmail(User user) {
        String token = createTokenFromUser(user);
        log.debug("Token = {}", token);
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
