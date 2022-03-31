package com.eureka.ationserver.dto.user;

import com.eureka.ationserver.model.user.User;
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

        private Long id;

        private String identifyId;

        private String name;

        private String email;

        private String myPageImgPath;


    }

    public static LoggedIn toLoggedIn(User user) {

        if (user == null) {
            return null;
        }

        LoggedIn loggedIn = new LoggedIn();
        loggedIn.id = user.getId();
        loggedIn.identifyId = user.getIdentifyId();
        loggedIn.name = user.getName();
        loggedIn.email = user.getEmail();
        loggedIn.myPageImgPath = user.getMyPageImgPath();

        return loggedIn;
    }


}
