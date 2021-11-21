package com.eureka.sensationserver.repository.persona;

import com.eureka.sensationserver.domain.persona.PersonaJob;
import com.eureka.sensationserver.domain.persona.PersonaSense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaSenseReposotiry extends JpaRepository<PersonaSense, Long> {
    List<PersonaSense> findByPersona_Id(@Param(value="personaId")Long personaId);
    List<PersonaSense> deleteByPersona_Id(@Param(value="personaId")Long personaId);


}
