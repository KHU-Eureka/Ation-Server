package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.user.User;
import io.swagger.annotations.ApiModelProperty;
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

        @ApiModelProperty(value = "페르소나 닉네임", required = true, position = 0)
        private String nickname;

        @ApiModelProperty(value = "페르소나 나이", required = true, position = 1)
        private Integer age;

        @ApiModelProperty(value = "페르소나 성별", required = true, position = 2)
        private Integer gender;

        @ApiModelProperty(value = "페르소나 mbti", required = true, position = 3)
        private String mbti;

        @ApiModelProperty(value = "페르소나 직업", required = true, position = 4)
        private String job;

        @ApiModelProperty(value = "페르소나 소개", required = true, position = 5)
        private String introduction;

        @ApiModelProperty(value = "페르소나 매력", required = true, position = 6)
        private List<String> charmList;

        @ApiModelProperty(value = "페르소나 발달감각", required = true, position = 7)
        private List<Long> senseIdList;

        @ApiModelProperty(value = "페르소나 분야", required = true, position = 8)
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
