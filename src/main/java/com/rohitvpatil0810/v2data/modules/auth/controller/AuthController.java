package com.rohitvpatil0810.v2data.modules.auth.controller;

import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.auth.dto.RegistrationRequest;
import com.rohitvpatil0810.v2data.modules.auth.dto.RegistrationResponse;
import com.rohitvpatil0810.v2data.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    ApiResponse<RegistrationResponse> register(@RequestBody @Valid RegistrationRequest request) {
        RegistrationResponse response = authService.registerUser(request);

        return new SuccessResponse<>("Registration Success", response);
    }
}
