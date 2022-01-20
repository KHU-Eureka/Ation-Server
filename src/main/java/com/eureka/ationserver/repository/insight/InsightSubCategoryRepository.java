package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.model.insight.InsightSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface InsightSubCategoryRepository extends JpaRepository<InsightSubCategory, Long> {
    List<InsightSubCategory> findByInsightMainCategory_Id(@Param(value="insightMainCategoryId") Long insightMainCategoryId);
    List<InsightSubCategory> findAllByIdIn(List<Long> id);

}
