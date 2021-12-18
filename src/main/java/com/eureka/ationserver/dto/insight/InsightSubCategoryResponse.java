package com.eureka.ationserver.dto.insight;

import com.eureka.ationserver.domain.insight.InsightCategory;
import com.eureka.ationserver.domain.insight.InsightSubCategory;
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

    public InsightSubCategoryResponse(InsightCategory insightCategory){
        id = insightCategory.getInsightSubCategory().getId();
        name = insightCategory.getInsightSubCategory().getName();
    }
}

