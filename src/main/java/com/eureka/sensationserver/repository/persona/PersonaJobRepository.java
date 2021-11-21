package com.eureka.sensationserver.repository.persona;

import com.eureka.sensationserver.domain.persona.PersonaJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaJobRepository extends JpaRepository<PersonaJob, Long> {
    List<PersonaJob> findByPersona_Id(@Param(value="personaId") Long personaId);
    List<PersonaJob> deleteByPersona_Id(@Param(value="personaId") Long personaId);


}
