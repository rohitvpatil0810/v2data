package com.rohitvpatil0810.v2data.modules.auth.controller;

import com.rohitvpatil0810.v2data.common.api.exceptions.BadRequestException;
import com.rohitvpatil0810.v2data.common.api.exceptions.BadTokenException;
import com.rohitvpatil0810.v2data.common.api.exceptions.TokenExpiredException;
import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.auth.dto.LoginRequest;
import com.rohitvpatil0810.v2data.modules.auth.dto.LoginResponse;
import com.rohitvpatil0810.v2data.modules.auth.dto.RefreshTokenRequest;
import com.rohitvpatil0810.v2data.modules.auth.dto.RegistrationRequest;
import com.rohitvpatil0810.v2data.modules.auth.service.AuthService;
import com.rohitvpatil0810.v2data.modules.auth.service.EmailVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final EmailVerificationService emailVerificationService;


    @PostMapping("/register")
    ApiResponse register(@RequestBody @Valid RegistrationRequest request) {
        authService.registerUser(request);

        return new SuccessResponse<>("If your email is not registered, you will receive a verification email shortly.");
    }

    @GetMapping("/verify")
    ApiResponse verifyEmail(@RequestParam @Valid String token) throws BadRequestException {
        emailVerificationService.verifyToken(token);

        return new SuccessResponse<>("Email verified Successfully");
    }

    @PostMapping("/login")
    ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);

        return new SuccessResponse<LoginResponse>("login successful", loginResponse);
    }

    @PostMapping("/refresh-token")
    ApiResponse<LoginResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) throws TokenExpiredException, BadTokenException {
        LoginResponse loginResponse = authService.refreshToken(refreshTokenRequest);

        return new SuccessResponse<>("Tokens Refreshed Successfully", loginResponse);
    }

    @PostMapping("/logout")
    ApiResponse logout(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        authService.logout(refreshTokenRequest);

        return new SuccessResponse<>("Logout successful");
    }
}
