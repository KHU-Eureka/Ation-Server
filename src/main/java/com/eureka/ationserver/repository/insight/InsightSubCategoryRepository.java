package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.model.insight.InsightSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsightSubCategoryRepository extends JpaRepository<InsightSubCategory, Long> {
}
