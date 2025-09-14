package com.rohitvpatil0810.v2data.modules.auth.service;

import com.rohitvpatil0810.v2data.modules.auth.dto.RegistrationRequest;
import com.rohitvpatil0810.v2data.modules.users.entity.User;
import com.rohitvpatil0810.v2data.modules.users.mapper.UserMapper;
import com.rohitvpatil0810.v2data.modules.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void registerUser(RegistrationRequest userData) {
        User user = userRepository.findByEmail(userData.getEmail())
                .orElseGet(() -> userMapper.toUserFromRegistrationRequest(userData));

        if (Boolean.TRUE.equals(user.getIsEmailVerified())) {
            return;
        }

        user.setName(userData.getName());
        user.setHashedPassword(hashPassword(userData.getPassword()));

        userRepository.save(user);
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
