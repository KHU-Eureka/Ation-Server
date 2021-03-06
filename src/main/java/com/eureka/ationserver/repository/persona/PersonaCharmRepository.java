package com.eureka.ationserver.repository.persona;

import com.eureka.ationserver.model.persona.PersonaCharm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaCharmRepository extends JpaRepository<PersonaCharm, Long> {
 List<PersonaCharm> deleteByPersona_Id(@Param(value="personaId")Long personaId);

}
