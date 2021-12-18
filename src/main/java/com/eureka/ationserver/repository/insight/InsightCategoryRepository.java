package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.InsightCategory;
import com.eureka.ationserver.domain.insight.InsightSubCategory;
import com.eureka.ationserver.domain.persona.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsightCategoryRepository extends JpaRepository<InsightCategory, Long> {
}
