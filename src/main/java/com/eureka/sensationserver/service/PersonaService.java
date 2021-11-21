package com.eureka.sensationserver.service;

import com.eureka.sensationserver.domain.User;
import com.eureka.sensationserver.domain.persona.Job;
import com.eureka.sensationserver.domain.persona.Persona;
import com.eureka.sensationserver.domain.persona.PersonaSense;
import com.eureka.sensationserver.domain.persona.Sense;
import com.eureka.sensationserver.dto.persona.PersonaRequest;
import com.eureka.sensationserver.repository.PersonaRepository;
import com.eureka.sensationserver.repository.PersonaSenseReposotiry;
import com.eureka.sensationserver.repository.SenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository personaRepository;
    private final SenseRepository senseRepository;
    private final PersonaSenseReposotiry personaSenseReposotiry;

    @Transactional
    public Long createPersona(User user, PersonaRequest personaRequest){
        Persona persona = personaRepository.save(personaRequest.toEntity(user));

        // sense
        List<Sense> senseList = senseRepository.findAllByIdIn(personaRequest.getSenseIdList());
        for(Sense sense : senseList){
            PersonaSense personaSense = PersonaSense.builder()
                                            .persona(persona)
                                            .sense(sense)
                                            .build();
            personaSenseReposotiry.save(personaSense);
            System.out.println(sense.toString());
        }

        List<Job> jobList = senseR
        return 1L;
    }
}
