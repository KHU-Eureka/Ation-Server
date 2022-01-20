package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.model.insight.InsightTag;
import com.eureka.ationserver.model.persona.PersonaCharm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface InsightTagRepository extends JpaRepository<InsightTag, Long> {
    List<PersonaCharm> findByInsight_Id(@Param(value="insightId")Long insightId);

}
