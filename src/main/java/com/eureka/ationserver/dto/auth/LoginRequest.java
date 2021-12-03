package com.eureka.ationserver.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
