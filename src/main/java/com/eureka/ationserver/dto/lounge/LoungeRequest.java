package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.lounge.Lounge;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.persona.Sense;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoungeRequest {

  @NotEmpty
  private String title;

  @NotEmpty
  private Long personaId;

  private Integer limitMember;

  private String introduction;

  private Long imageId;

  private Long senseId;

  private List<String> tagList;

  private Long mainCategoryId;

  private List<Long> subCategoryIdList;

  public Lounge toEntity(Persona persona, MainCategory mainCategory, Sense sense, String imgPath) {
    return Lounge.builder()
        .title(this.title)
        .limitMember(this.limitMember)
        .introduction(this.introduction)
        .imgPath(imgPath)
        .loungeMainCategory(mainCategory)
        .sense(sense)
        .persona(persona)
        .build();
  }

}