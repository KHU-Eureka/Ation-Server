package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.Insight;
import com.eureka.ationserver.domain.insight.Pin;
import com.eureka.ationserver.domain.insight.PinBoard;
import com.eureka.ationserver.domain.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PinRepository extends JpaRepository<Pin, Long> {
    List<Pin> findByPinBoard_PersonaAndInsight_TitleContaining(Persona persona, String keyword);
    List<Pin> findByPinBoard(PinBoard pinBoard);

}
