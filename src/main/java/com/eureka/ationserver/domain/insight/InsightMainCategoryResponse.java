package com.eureka.ationserver.domain.insight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightMainCategoryResponse {
    private Long id;
    private String name;

    public InsightMainCategoryResponse(InsightMainCategory insightMainCategory){
        id = insightMainCategory.getId();
        name = insightMainCategory.getName();
    }
}
