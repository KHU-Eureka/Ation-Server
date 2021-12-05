package com.eureka.ationserver.dto.insight;

import com.eureka.ationserver.domain.insight.InsightMainCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


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
