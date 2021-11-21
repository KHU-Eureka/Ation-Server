package com.eureka.sensationserver.service;

import com.eureka.sensationserver.advice.exception.ForbiddenException;
import com.eureka.sensationserver.domain.User;
import com.eureka.sensationserver.domain.persona.*;
import com.eureka.sensationserver.dto.persona.*;
import com.eureka.sensationserver.repository.persona.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository personaRepository;
    private final SenseRepository senseRepository;
    private final PersonaSenseReposotiry personaSenseReposotiry;
    private final JobRepository jobRepository;
    private final InterestRepository interestRepository;
    private final PersonaInterestRepository personaInterestRepository;
    private final PersonaCharmRepository personaCharmRepository;
    private final PersonaJobRepository personaJobRepository;


    @Transactional
    public Long save(User user, PersonaRequest personaRequest){
        Persona persona = personaRepository.save(personaRequest.toEntity(user));

        // sense
        List<Sense> senseList = senseRepository.findAllByIdIn(personaRequest.getSenseIdList());
        for(Sense sense : senseList){
            PersonaSense personaSense = PersonaSense.builder()
                                            .persona(persona)
                                            .sense(sense)
                                            .build();
            personaSenseReposotiry.save(personaSense);
        }

        // job
        List<Job> jobList = jobRepository.findAllByIdIn(personaRequest.getJobIdList());
        for(Job job : jobList){
            PersonaJob personaJob = PersonaJob.builder()
                                        .persona(persona)
                                        .job(job)
                                        .build();
            personaJobRepository.save(personaJob);
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


    @Transactional(readOnly = true)
    public PersonaResponse find(Long personaId){
        Persona persona = personaRepository.getById(personaId);
        List<String> charmList = new ArrayList<>();
        personaCharmRepository.findByPersona_Id(personaId).stream().forEach(x -> charmList.add(x.getName()));

        List<SenseResponse> senseResponseList = personaSenseReposotiry.findByPersona_Id(personaId).stream().map(SenseResponse::new).collect(Collectors.toList());

        List<JobResponse> jobResponseList = personaJobRepository.findByPersona_Id(personaId).stream().map(JobResponse::new).collect(Collectors.toList());
        List<InterestResponse> interestResponseList = personaInterestRepository.findByPersona_Id(personaId).stream().map(InterestResponse::new).collect(Collectors.toList());

        return new PersonaResponse(persona, charmList, senseResponseList, jobResponseList, interestResponseList);

    }
    public List<PersonaResponse> findAll(User user){
        List<Persona> personaList = personaRepository.findByUserId(user.getId());
        List<PersonaResponse> personaResponseList = new ArrayList<>();
        for (Persona persona : personaList){
            List<String> charmList = new ArrayList<>();
            personaCharmRepository.findByPersona_Id(persona.getId()).stream().forEach(x -> charmList.add(x.getName()));

            List<SenseResponse> senseResponseList = personaSenseReposotiry.findByPersona_Id(persona.getId()).stream().map(SenseResponse::new).collect(Collectors.toList());

            List<JobResponse> jobResponseList = personaJobRepository.findByPersona_Id(persona.getId()).stream().map(JobResponse::new).collect(Collectors.toList());
            List<InterestResponse> interestResponseList = personaInterestRepository.findByPersona_Id(persona.getId()).stream().map(InterestResponse::new).collect(Collectors.toList());

            personaResponseList.add(new PersonaResponse(persona, charmList, senseResponseList, jobResponseList, interestResponseList));
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

            personaJobRepository.deleteByPersona_Id(personaId);
            List<Job> jobList = jobRepository.findAllByIdIn(personaRequest.getJobIdList());
            for (Job job : jobList) {
                PersonaJob personaJob = PersonaJob.builder()
                        .persona(persona)
                        .job(job)
                        .build();
                personaJobRepository.save(personaJob);
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
        Persona persona = personaRepository.getById(personaId);
        user.setPersona(persona);
        return personaId;
    }

    @Transactional(readOnly = true)
    public Long getCurrPersona(User user){
        Persona persona = user.getPersona();
        if(persona == null){
            return null;
        }else{
            return user.getPersona().getId();

        }
    }
}
