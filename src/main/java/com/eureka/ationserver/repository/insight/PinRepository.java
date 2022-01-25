package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.model.insight.Pin;
import com.eureka.ationserver.model.insight.PinBoard;
import com.eureka.ationserver.model.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public interface PinRepository extends JpaRepository<Pin, Long> {
    Set<Pin> findByPinBoard_PersonaAndInsight_TitleContainingOrderByCreatedAtDesc(Persona persona, String keyword);
    Set<Pin> findByPinTagList_NameContainingOrderByCreatedAtDesc(String keyword);
    List<Pin> findByPinBoard(PinBoard pinBoard);
    List<Pin> findByPinBoardOrderByModifiedAtDesc(PinBoard pinBoard);
    Long countByPinBoard(PinBoard pinBoard);
    List<Pin> findByPinBoard_Persona(Persona persona);

}
