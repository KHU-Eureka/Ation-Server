package com.eureka.ationserver.dto.category;

import com.eureka.ationserver.model.insight.InsightSubCategory;
import com.eureka.ationserver.model.category.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryResponse {
    private Long id;
    private String name;

    public SubCategoryResponse(SubCategory subCategory){
        id = subCategory.getId();
        name = subCategory.getName();
    }

    public SubCategoryResponse(InsightSubCategory insightSubCategory){
        id = insightSubCategory.getSubCategory().getId();
        name = insightSubCategory.getSubCategory().getName();
    }
}

