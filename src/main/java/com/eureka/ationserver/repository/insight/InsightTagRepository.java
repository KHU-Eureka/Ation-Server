package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.InsightTag;
import com.eureka.ationserver.domain.persona.PersonaCharm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsightTagRepository extends JpaRepository<InsightTag, Long> {
    List<PersonaCharm> findByInsight_Id(@Param(value="insightId")Long insightId);

}
