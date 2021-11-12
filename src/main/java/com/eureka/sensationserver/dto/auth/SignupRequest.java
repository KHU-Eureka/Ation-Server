package com.eureka.sensationserver.dto.auth;

import com.eureka.sensationserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class SignupRequest {

    private String email;

    @Size(min = 3, max = 20)
    private String password;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

}
