package com.eureka.ationserver.dto.user;

import com.eureka.ationserver.model.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class UserResponse {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Out {

    @ApiModelProperty(value = "유저 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "유저 식별번호", position = 1)
    private String identifyId;

    @ApiModelProperty(value = "유저 이름", position = 2)
    private String name;

    @ApiModelProperty(value = "유저 이메일", position = 3)
    private String email;

    @ApiModelProperty(value = "유저 마이페이지 이미지", position = 4)
    private String myPageImgPath;


  }

  public static class IdOut {

    @ApiModelProperty(value = "유저 id", position = 0)
    private Long id;
  }

  public static Out toOut(User user) {

    if (user == null) {
      return null;
    }

    Out out = new Out();
    out.id = user.getId();
    out.identifyId = user.getIdentifyId();
    out.name = user.getName();
    out.email = user.getEmail();
    out.myPageImgPath = user.getMyPageImgPath();

    return out;
  }

  public static IdOut toIdOut(Long id) {
    if (id == null) {
      return null;
    }
    IdOut idOut = new IdOut();
    idOut.id = id;
    return idOut;
  }


}
