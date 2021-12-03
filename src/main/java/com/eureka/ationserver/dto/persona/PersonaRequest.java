package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.domain.persona.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaRequest {

    @NotEmpty
    private String nickname;

    private Integer age;

    private Integer gender;

    private String mbti;

    private String job;

    private List<String> charmList;

    private List<Long> senseIdList;

    private List<Long> interestIdList;


    public Persona toEntity(User user, String defaultPath){
        return Persona.builder()
                .user(user)
                .nickname(nickname)
                .age(age)
                .gender(gender)
                .mbti(mbti)
                .job(job)
                .profileImgPath(defaultPath)
                .build();
    }


}
