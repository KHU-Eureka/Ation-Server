package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.model.insight.InsightMainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsightMainCategoryRepository extends JpaRepository<InsightMainCategory, Long> {
}
