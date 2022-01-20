package com.eureka.ationserver.repository.persona;

import com.eureka.ationserver.model.persona.PersonaSense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaSenseReposotiry extends JpaRepository<PersonaSense, Long> {
    List<PersonaSense> findByPersona_Id(@Param(value="personaId")Long personaId);
    List<PersonaSense> deleteByPersona_Id(@Param(value="personaId")Long personaId);


}
