package com.eureka.ationserver.dto.category;

import com.eureka.ationserver.model.category.MainCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainCategoryResponse {
    private Long id;
    private String name;

    public MainCategoryResponse(MainCategory mainCategory){
        id = mainCategory.getId();
        name = mainCategory.getName();
    }
}
