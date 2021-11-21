package com.eureka.sensationserver.dto.persona;

import com.eureka.sensationserver.domain.User;
import com.eureka.sensationserver.domain.persona.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaRequest {
    private String name;

    private Integer age;

    private String gender;

    private String mbti;

    private List<String> charmList;

    private List<Long> senseIdList;

    private List<Long> interestIdList;

    private List<Long> jobIdList;

    public Persona toEntity(User user){
        return Persona.builder()
                .user(user)
                .name(name)
                .age(age)
                .gender(gender)
                .mbti(mbti)
                .build();
    }


}
