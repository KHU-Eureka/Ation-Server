package com.eureka.sensationserver.repository.persona;

import com.eureka.sensationserver.domain.persona.PersonaCharm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaCharmRepository extends JpaRepository<PersonaCharm, Long> {
 List<PersonaCharm> findByPersona_Id(@Param(value="personaId")Long personaId);
 List<PersonaCharm> deleteByPersona_Id(@Param(value="personaId")Long personaId);

}
