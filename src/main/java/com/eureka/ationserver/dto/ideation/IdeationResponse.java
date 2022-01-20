package com.eureka.ationserver.dto.ideation;

import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import com.eureka.ationserver.model.ideation.Ideation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IdeationResponse {
  private Long id;

  private PersonaSimpleResponse persona;

  private String title;

  private String imgPath;

  private String content;

  public IdeationResponse(Ideation ideation){
    this.id = ideation.getId();
    this.persona = new PersonaSimpleResponse(ideation.getPersona());
    this.title = ideation.getTitle();
    this.imgPath = ideation.getImgPath();

  }

}
