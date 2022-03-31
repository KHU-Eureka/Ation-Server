package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.user.User;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class PersonaRequest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class In {

        @NotEmpty
        private String nickname;

        private Integer age;

        private Integer gender;

        private String mbti;

        private String job;

        private String introduction;

        private List<String> charmList;

        private List<Long> senseIdList;

        private List<Long> interestIdList;

        public Persona toPersona(User user, String defaultPath) {
            return Persona.builder()
                .user(user)
                .nickname(nickname)
                .age(age)
                .gender(gender)
                .mbti(mbti)
                .job(job)
                .introduction(introduction)
                .profileImgPath(defaultPath)
                .build();
        }
    }


}
