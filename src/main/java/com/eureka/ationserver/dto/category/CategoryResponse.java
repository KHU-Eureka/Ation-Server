package com.eureka.ationserver.dto.category;

import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.category.SubCategory;
import com.eureka.ationserver.model.insight.InsightSubCategory;
import com.eureka.ationserver.model.lounge.LoungeSubCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CategoryResponse {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Main{

    @ApiModelProperty(value = "메인 카테고리 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "메인 카테고리 이름", position = 1)
    private String name;

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Sub{

    @ApiModelProperty(value = "서브 카테고리 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "서브 카테고리 이름", position = 1)
    private String name;

  }

  public static Main toMain(MainCategory mainCategory){
    if(mainCategory == null){
      return null;
    }

    Main main = new Main();
    main.id = mainCategory.getId();
    main.name = mainCategory.getName();
    return main;
  }

  public static Sub toSub(SubCategory subCategory){
    if(subCategory == null){
      return null;
    }

    Sub sub = new Sub();
    sub.id = subCategory.getId();
    sub.name = subCategory.getName();
    return sub;
  }

  public static Sub toSub(InsightSubCategory subCategory){
    if(subCategory == null){
      return null;
    }

    Sub sub = new Sub();
    sub.id = subCategory.getSubCategory().getId();
    sub.name = subCategory.getSubCategory().getName();
    return sub;
  }

  public static Sub toSub(LoungeSubCategory subCategory){
    if(subCategory == null){
      return null;
    }

    Sub sub = new Sub();
    sub.id = subCategory.getSubCategory().getId();
    sub.name = subCategory.getSubCategory().getName();
    return sub;
  }


}
