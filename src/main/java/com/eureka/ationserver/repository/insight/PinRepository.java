package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.Insight;
import com.eureka.ationserver.domain.insight.Pin;
import com.eureka.ationserver.domain.insight.PinBoard;
import com.eureka.ationserver.domain.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PinRepository extends JpaRepository<Pin, Long> {
    Set<Pin> findByPinBoard_PersonaAndInsight_TitleContainingOrderByCreatedAtDesc(Persona persona, String keyword);
    Set<Pin> findByPinTagList_NameContainingOrderByCreatedAtDesc(String keyword);
    List<Pin> findByPinBoard(PinBoard pinBoard);
    Long countByPinBoard(PinBoard pinBoard);
    List<Pin> findByPinBoard_Persona(Persona persona);

}
