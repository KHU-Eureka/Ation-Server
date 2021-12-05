package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.PinTag;
import com.eureka.ationserver.domain.persona.PersonaCharm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PinTagRepository extends JpaRepository<PinTag, Long> {
    List<PinTag> deleteByPin_Id(@Param(value="pinId")Long pinId);

}
