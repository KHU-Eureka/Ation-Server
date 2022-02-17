package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.model.lounge.Lounge;
import com.eureka.ationserver.model.lounge.LoungeImage;
import com.eureka.ationserver.utils.image.ImageUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoungeImageResponse {

  private Long id;

  private String imgPath;

  public LoungeImageResponse(LoungeImage loungeImage) {
    this.id = loungeImage.getId();
    this.imgPath = ImageUtil.getImagePath(Lounge.Lounge_PREFIX, this.id).get(0);
  }
}
