package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.dto.persona.PersonaRequest;
import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.model.persona.Interest;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.persona.PersonaCharm;
import com.eureka.ationserver.model.persona.PersonaInterest;
import com.eureka.ationserver.model.persona.PersonaSense;
import com.eureka.ationserver.model.persona.Sense;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.persona.InterestRepository;
import com.eureka.ationserver.repository.persona.PersonaCharmRepository;
import com.eureka.ationserver.repository.persona.PersonaInterestRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import com.eureka.ationserver.repository.persona.PersonaSenseReposotiry;
import com.eureka.ationserver.repository.persona.SenseRepository;
import com.eureka.ationserver.utils.image.ImageUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository personaRepository;
    private final SenseRepository senseRepository;
    private final PersonaSenseReposotiry personaSenseReposotiry;
    private final InterestRepository interestRepository;
    private final PersonaInterestRepository personaInterestRepository;
    private final PersonaCharmRepository personaCharmRepository;
    private final AuthService authService;


    @Transactional
    public Long save(PersonaRequest personaRequest) {

        User user = authService.auth();

        String defaultPath = ImageUtil.getDefaultImagePath(Persona.PERSONA_PREFIX);

        Persona persona = personaRepository.save(personaRequest.toEntity(user, defaultPath));

        // sense
        List<Sense> senseList = senseRepository.findAllByIdIn(personaRequest.getSenseIdList());
        for (Sense sense : senseList) {
            PersonaSense personaSense = PersonaSense.builder()
                .persona(persona)
                                            .sense(sense)
                                            .build();
            personaSenseReposotiry.save(personaSense);
        }


        // interest
        List<Interest> interestList = interestRepository.findAllByIdIn(personaRequest.getInterestIdList());
        for(Interest interest : interestList){
            PersonaInterest personaInterest = PersonaInterest.builder()
                                                .persona(persona)
                                                .interest(interest)
                                                .build();
            personaInterestRepository.save(personaInterest);
        }

        // charm
        for(String charm : personaRequest.getCharmList()){
            PersonaCharm personaCharm = PersonaCharm.builder()
                                            .persona(persona)
                                            .name(charm)
                                            .build();
            personaCharmRepository.save(personaCharm);
        }


        return persona.getId();
    }

    public Boolean duplicate(String nickname){
        Optional<Persona> persona = personaRepository.findByNickname(nickname);
        if(persona.isPresent()) {
            return true;
        }else{
            return false;
        }
    }


    @Transactional
    public Long saveImg(Long personaId, MultipartFile profileImg) throws IOException {
        User user = authService.auth();

        Persona persona = personaRepository.getById(personaId);
        if (user.getId() != persona.getUser().getId()) {
            throw new ForbiddenException();
        } else {
            List<String> pathList = ImageUtil.getImagePath(Persona.PERSONA_PREFIX, personaId);
            File file = new File(pathList.get(1));
            profileImg.transferTo(file);

            persona.setProfileImgPath(pathList.get(0));
            return personaId;
        }
    }


    @Transactional(readOnly = true)
    public PersonaResponse find(Long personaId) {
        Persona persona = personaRepository.getById(personaId);
        return new PersonaResponse(persona);

    }

    public List<PersonaResponse> findAll() {
        User user = authService.auth();
        List<Persona> personaList = personaRepository.findByUserId(user.getId());
        List<PersonaResponse> personaResponseList = new ArrayList<>();
        for (Persona persona : personaList) {
            personaResponseList.add(new PersonaResponse(persona));
        }
        return personaResponseList;


    }


    @Transactional
    public Long update(Long personaId, PersonaRequest personaRequest) {

        User user = authService.auth();

        Persona persona = personaRepository.getById(personaId);
        if (user.getId() != persona.getUser().getId()) {
            throw new ForbiddenException();
        } else {
            persona.update(personaRequest);

            personaSenseReposotiry.deleteByPersona_Id(personaId);
            List<Sense> senseList = senseRepository.findAllByIdIn(personaRequest.getSenseIdList());
            for (Sense sense : senseList) {
                PersonaSense personaSense = PersonaSense.builder()
                        .persona(persona)
                        .sense(sense)
                        .build();
                personaSenseReposotiry.save(personaSense);
            }


            personaInterestRepository.deleteByPersona_Id(personaId);
            List<Interest> interestList = interestRepository.findAllByIdIn(personaRequest.getInterestIdList());
            for (Interest interest : interestList) {
                PersonaInterest personaInterest = PersonaInterest.builder()
                        .persona(persona)
                        .interest(interest)
                        .build();
                personaInterestRepository.save(personaInterest);
            }

            personaCharmRepository.deleteByPersona_Id(personaId);
            for (String charm : personaRequest.getCharmList()) {
                PersonaCharm personaCharm = PersonaCharm.builder()
                        .persona(persona)
                        .name(charm)
                        .build();
                personaCharmRepository.save(personaCharm);
            }

            return personaId;
        }
    }

    @Transactional
    public Long delete(Long personaId) {

        User user = authService.auth();

        Persona persona = personaRepository.getById(personaId);
        if (user.getId() != persona.getUser().getId()) {
            throw new ForbiddenException();
        } else {
            personaRepository.deleteById(personaId);
            return personaId;
        }
    }

    @Transactional
    public Long setCurrentPersona(Long personaId) {

        User user = authService.auth();

        Persona persona = personaRepository.getById(personaId);
        if (user.getId() != persona.getUser().getId()) {
            throw new ForbiddenException();
        } else {
            user.setPersona(persona);
            return personaId;
        }
    }

    @Transactional(readOnly = true)
    public PersonaResponse getCurrentPersona() {
        User user = authService.auth();
        Persona persona = user.getPersona();
        if (persona == null) {
            return null;
        } else {
            return new PersonaResponse(persona);
        }

    }
}
