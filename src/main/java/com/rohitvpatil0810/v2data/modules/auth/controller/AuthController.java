package com.rohitvpatil0810.v2data.modules.auth.controller;

import com.rohitvpatil0810.v2data.common.api.exceptions.BadRequestException;
import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.auth.dto.RegistrationRequest;
import com.rohitvpatil0810.v2data.modules.auth.service.AuthService;
import com.rohitvpatil0810.v2data.modules.auth.service.EmailVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
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
}
