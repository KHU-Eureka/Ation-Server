package com.eureka.ationserver.dto.user;

import com.eureka.ationserver.model.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class UserResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoggedIn {

        @ApiModelProperty(value = "유저 id", position = 0)
        private Long userId;

        @ApiModelProperty(value = "유저 식별번호", position = 1)
        private String identifyId;

        @ApiModelProperty(value = "유저 이름", position = 2)
        private String name;

        @ApiModelProperty(value = "유저 이메일", position = 3)
        private String email;

        @ApiModelProperty(value = "유저 마이페이지 이미지", position = 4)
        private String myPageImgPath;


    }

    public static LoggedIn toLoggedIn(User user) {

        if (user == null) {
            return null;
        }

        LoggedIn loggedIn = new LoggedIn();
        loggedIn.userId = user.getId();
        loggedIn.identifyId = user.getIdentifyId();
        loggedIn.name = user.getName();
        loggedIn.email = user.getEmail();
        loggedIn.myPageImgPath = user.getMyPageImgPath();

        return loggedIn;
    }


}
