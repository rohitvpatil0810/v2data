package com.rohitvpatil0810.v2data.modules.auth.service;

import com.rohitvpatil0810.v2data.common.security.JwtUtil;
import com.rohitvpatil0810.v2data.common.security.TokenHasher;
import com.rohitvpatil0810.v2data.modules.auth.dto.LoginRequest;
import com.rohitvpatil0810.v2data.modules.auth.dto.LoginResponse;
import com.rohitvpatil0810.v2data.modules.auth.dto.RegistrationRequest;
import com.rohitvpatil0810.v2data.modules.auth.entity.RefreshToken;
import com.rohitvpatil0810.v2data.modules.auth.event.UserRegisteredEvent;
import com.rohitvpatil0810.v2data.modules.auth.repository.RefreshTokenRepository;
import com.rohitvpatil0810.v2data.modules.users.entity.User;
import com.rohitvpatil0810.v2data.modules.users.mapper.UserMapper;
import com.rohitvpatil0810.v2data.modules.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailVerificationService emailVerificationService;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void registerUser(RegistrationRequest userData) {
        User user = userRepository.findByEmail(userData.getEmail())
                .orElseGet(() -> userMapper.toUserFromRegistrationRequest(userData));

        if (Boolean.TRUE.equals(user.getIsEmailVerified())) {
            return;
        }

        user.setName(userData.getName());
        user.setHashedPassword(hashPassword(userData.getPassword()));

        User savedUser = userRepository.save(user);

        // Publish event after saving user
        eventPublisher.publishEvent(new UserRegisteredEvent(savedUser.getId()));
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String refreshToken = jwtUtil.generateRefreshToken(user);
        String accessToken = jwtUtil.generateAccessToken(user);

        RefreshToken refreshTokenRecord = RefreshToken.builder()
                .hashedRefreshToken(TokenHasher.hashToken(refreshToken))
                .user(user)
                .build();

        refreshTokenRepository.save(refreshTokenRecord);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
