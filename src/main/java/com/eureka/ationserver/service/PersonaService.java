package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.model.persona.*;
import com.eureka.ationserver.dto.persona.*;
import com.eureka.ationserver.repository.persona.*;
import com.eureka.ationserver.util.image.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository personaRepository;
    private final SenseRepository senseRepository;
    private final PersonaSenseReposotiry personaSenseReposotiry;
    private final InterestRepository interestRepository;
    private final PersonaInterestRepository personaInterestRepository;
    private final PersonaCharmRepository personaCharmRepository;


    @Transactional
    public Long save(User user, PersonaRequest personaRequest){
        String defaultPath = ImageUtil.getDefaultImagePath(Persona.PERSONA_PREFIX);

        Persona persona = personaRepository.save(personaRequest.toEntity(user, defaultPath));

        // sense
        List<Sense> senseList = senseRepository.findAllByIdIn(personaRequest.getSenseIdList());
        for(Sense sense : senseList){
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


    @Value("${eureka.app.publicIp}")
    private String HOST;

    @Value("${server.port}")
    private String PORT;

    @Value("${eureka.app.imagePath}")
    private String IMAGEPATH;


    @Transactional
    public Long saveImg(User user, Long personaId, MultipartFile profileImg) throws IOException {
        Persona persona = personaRepository.getById(personaId);
        if(user.getId() != persona.getUser().getId()){
            throw new ForbiddenException();
        } else {
            List<String> pathList = ImageUtil.getImagePath(Persona.PERSONA_PREFIX,personaId);
            File file = new File(pathList.get(1));
            profileImg.transferTo(file);

            persona.setProfileImgPath(pathList.get(0));
            return personaId;
        }
    }


    @Transactional(readOnly = true)
    public PersonaResponse find(Long personaId){
        Persona persona = personaRepository.getById(personaId);
         return new PersonaResponse(persona);

    }
    public List<PersonaResponse> findAll(User user){
        List<Persona> personaList = personaRepository.findByUserId(user.getId());
        List<PersonaResponse> personaResponseList = new ArrayList<>();
        for (Persona persona : personaList){
            personaResponseList.add(new PersonaResponse(persona));
        }
        return personaResponseList;


    }



    @Transactional
    public Long update(User user, Long personaId, PersonaRequest personaRequest){
        Persona persona = personaRepository.getById(personaId);
        if(user.getId() != persona.getUser().getId()){
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
    public Long delete(User user, Long personaId){
        Persona persona = personaRepository.getById(personaId);
        if(user.getId() != persona.getUser().getId()){
            throw new ForbiddenException();
        } else {
            personaRepository.deleteById(personaId);
            return personaId;
        }
    }

    @Transactional
    public Long setCurrentPersona(User user, Long personaId){
        System.out.println(personaId);
        Persona persona = personaRepository.getById(personaId);
        if(user.getId() != persona.getUser().getId()){
            throw new ForbiddenException();
        } else {
            user.setPersona(persona);
            return personaId;
        }
    }

    @Transactional(readOnly = true)
    public PersonaResponse getCurrentPersona(User user){
        Persona persona = user.getPersona();
        if(persona == null){
            return null;
        }else{
            return new PersonaResponse(persona);
        }
    }
}
