package com.eureka.ationserver.dto.ideation;

import com.eureka.ationserver.model.ideation.Ideation;
import com.eureka.ationserver.model.persona.Persona;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IdeationRequest {

  @NotEmpty
  private Long personaId;

  @NotEmpty
  private String title;

  @NotEmpty
  private String content;

  public Ideation toEntity(Persona persona, String imgPath){
    return Ideation.builder()
        .persona(persona)
        .imgPath(imgPath)
        .title(title)
        .content(content)
        .build();
  }


}
