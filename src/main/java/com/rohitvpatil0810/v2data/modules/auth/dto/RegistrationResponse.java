package com.rohitvpatil0810.v2data.modules.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {
    private Long id;
    private String name;
    private String email;
    private Boolean isEmailVerified;
}
