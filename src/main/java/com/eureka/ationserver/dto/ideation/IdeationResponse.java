package com.eureka.ationserver.dto.ideation;

import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.model.ideation.Ideation;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IdeationResponse {
  private Long id;

  private PersonaResponse.Out persona;

  private String title;

  private String imgPath;

  private String whiteboard;

  private LocalDateTime createdAt;

  public IdeationResponse(Ideation ideation){
    this.id = ideation.getId();
    this.persona = PersonaResponse.toOut(ideation.getPersona());
    this.title = ideation.getTitle();
    this.whiteboard = ideation.getWhiteboard();
    this.imgPath = ideation.getImgPath();
    this.createdAt = ideation.getCreatedAt();

  }

}
