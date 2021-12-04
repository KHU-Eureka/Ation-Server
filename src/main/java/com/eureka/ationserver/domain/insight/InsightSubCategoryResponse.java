package com.eureka.ationserver.domain.insight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightSubCategoryResponse {
    private Long id;
    private String name;

    public InsightSubCategoryResponse(InsightSubCategory insightSubCategory){
        id = insightSubCategory.getId();
        name = insightSubCategory.getName();
    }
}