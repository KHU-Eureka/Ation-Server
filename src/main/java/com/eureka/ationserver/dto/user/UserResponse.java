package com.eureka.ationserver.dto.user;

import com.eureka.ationserver.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private String email;

    private Long id;

    private String name;

    private String myPageImgPath;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.myPageImgPath = user.getMyPageImgPath();
    }


}
