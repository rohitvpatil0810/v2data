package com.rohitvpatil0810.v2data.modules.auth.listener;

import com.rohitvpatil0810.v2data.modules.auth.event.UserRegisteredEvent;
import com.rohitvpatil0810.v2data.modules.auth.service.EmailVerificationService;
import com.rohitvpatil0810.v2data.modules.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisteredListener {

    private final EmailVerificationService emailVerificationService;
    private final UserRepository userRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(UserRegisteredEvent event) {
        userRepository.findById(event.getUserId()).ifPresent(user -> {
            try {
                emailVerificationService.sendVerificationEmail(user);
                log.info("Verification email sent to {}", user.getEmail());
            } catch (Exception e) {
                log.error("Failed to send verification email to {}", user.getEmail());
            }
        });
    }
}
