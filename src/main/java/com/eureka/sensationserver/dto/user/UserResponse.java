package com.eureka.sensationserver.dto.user;

import com.eureka.sensationserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
public class UserResponse {
    private String email;

    private Long id;

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.id = user.getId();
    }


}
