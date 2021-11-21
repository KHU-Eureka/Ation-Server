package com.eureka.sensationserver.repository.insight;

import com.eureka.sensationserver.domain.insight.Insight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsightRepository extends JpaRepository<Insight, Long> {
}
