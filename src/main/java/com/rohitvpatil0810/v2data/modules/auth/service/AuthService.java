package com.rohitvpatil0810.v2data.modules.auth.service;

import com.rohitvpatil0810.v2data.modules.auth.dto.RegistrationRequest;
import com.rohitvpatil0810.v2data.modules.auth.dto.RegistrationResponse;
import com.rohitvpatil0810.v2data.modules.users.entity.User;
import com.rohitvpatil0810.v2data.modules.users.mapper.UserMapper;
import com.rohitvpatil0810.v2data.modules.users.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public RegistrationResponse registerUser(RegistrationRequest userData) {
        String hashedPassword = hashPassword(userData.getPassword());
        User user = userMapper.toUserFromRegistrationRequest(userData);

        user.setHashedPassword(hashedPassword);

        User savedUser = userRepository.save(user);

        return userMapper.toRegistrationResponseFromUser(savedUser);
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }


}
