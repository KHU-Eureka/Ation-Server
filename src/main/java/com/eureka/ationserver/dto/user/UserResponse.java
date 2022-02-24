package com.eureka.ationserver.dto.user;

import com.eureka.ationserver.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String identifyId;

    private String name;

    private String email;

    private String myPageImgPath;

    public UserResponse(User user) {
        this.id = user.getId();
        this.identifyId = user.getIdentifyId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.myPageImgPath = user.getMyPageImgPath();
    }


}
