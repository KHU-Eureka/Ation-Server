package com.eureka.sensationserver.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class LoginRequest {

    private String email;

    @Size(min = 3, max = 20)
    private String password;
}
