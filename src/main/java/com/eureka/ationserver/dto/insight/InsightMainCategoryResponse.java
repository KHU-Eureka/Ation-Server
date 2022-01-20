package com.eureka.ationserver.dto.insight;

import com.eureka.ationserver.model.insight.InsightMainCategory;
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
