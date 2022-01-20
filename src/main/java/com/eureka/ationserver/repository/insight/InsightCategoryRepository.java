package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.model.insight.InsightCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsightCategoryRepository extends JpaRepository<InsightCategory, Long> {
}
