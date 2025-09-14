package com.rohitvpatil0810.v2data.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
