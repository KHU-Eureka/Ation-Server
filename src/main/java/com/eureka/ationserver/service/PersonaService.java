package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.dto.persona.PersonaRequest;
import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.dto.persona.category.InterestResponse;
import com.eureka.ationserver.dto.persona.category.SenseResponse;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.persona.PersonaCharm;
import com.eureka.ationserver.model.persona.PersonaInterest;
import com.eureka.ationserver.model.persona.PersonaSense;
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
import java.util.stream.Collectors;
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
  private final UserService userService;


  @Transactional
  public PersonaResponse.IdOut save(PersonaRequest.In in) {

    User user = userService.auth();

    String defaultPath = ImageUtil.getDefaultImagePath(Persona.PERSONA_PREFIX);

    Persona persona = personaRepository.save(in.toPersona(user, defaultPath));

    // sense
    List<PersonaSense> personaSenseList = new ArrayList<>();
    senseRepository.findAllByIdIn(in.getSenseIdList()).forEach(sense -> personaSenseList.add(
        PersonaSense.builder()
            .persona(persona)
            .sense(sense)
            .build())
    );
    personaSenseReposotiry.saveAll(personaSenseList);

    // interest
    List<PersonaInterest> personaInterestList = new ArrayList<>();
    interestRepository.findAllByIdIn(in.getInterestIdList())
        .forEach(interest -> personaInterestList.add(
            PersonaInterest.builder()
                .persona(persona)
                .interest(interest)
                .build()
        ));
    personaInterestRepository.saveAll(personaInterestList);

    // charm
    List<PersonaCharm> personaCharmList = new ArrayList<>();
    in.getCharmList().forEach(charm -> personaCharmList.add(
        PersonaCharm.builder()
            .persona(persona)
            .name(charm)
            .build()
    ));
    personaCharmRepository.saveAll(personaCharmList);

    return PersonaResponse.toIdOut(persona.getId());
  }

  @Transactional
  public PersonaResponse.IdOut saveImg(Long personaId, MultipartFile profileImg)
      throws IOException {
    User user = userService.auth();

    Persona persona = personaRepository.getById(personaId);
    if (user.getId() != persona.getUser().getId()) {
      throw new ForbiddenException();
    } else {
      List<String> pathList = ImageUtil.getImagePath(Persona.PERSONA_PREFIX, personaId);
      File file = new File(pathList.get(1));
      profileImg.transferTo(file);

      persona.setProfileImgPath(pathList.get(0));
      return PersonaResponse.toIdOut(personaId);
    }
  }


  @Transactional(readOnly = true)
  public PersonaResponse.Out find(Long personaId) {
    Persona persona = personaRepository.getById(personaId);
    return PersonaResponse.toOut(persona);
  }

  public List<PersonaResponse.Out> findAll() {

    User user = userService.auth();

    List<Persona> personaList = personaRepository.findByUserId(user.getId());
    List<PersonaResponse.Out> outList = new ArrayList<>();
    personaList.forEach(x -> outList.add(PersonaResponse.toOut(x)));

    return outList;

  }


  @Transactional
  public PersonaResponse.IdOut update(Long personaId, PersonaRequest.In in) {

    User user = userService.auth();

    Persona persona = personaRepository.getById(personaId);
    if (user.getId() != persona.getUser().getId()) {
      throw new ForbiddenException();
    } else {
      persona.update(in);

      // sense
      personaSenseReposotiry.deleteByPersona_Id(personaId);
      List<PersonaSense> personaSenseList = new ArrayList<>();
      senseRepository.findAllByIdIn(in.getSenseIdList()).forEach(sense -> personaSenseList.add(
          PersonaSense.builder()
              .persona(persona)
              .sense(sense)
              .build())
      );
      personaSenseReposotiry.saveAll(personaSenseList);

      // interest
      personaInterestRepository.deleteByPersona_Id(personaId);
      List<PersonaInterest> personaInterestList = new ArrayList<>();
      interestRepository.findAllByIdIn(in.getInterestIdList())
          .forEach(interest -> personaInterestList.add(
              PersonaInterest.builder()
                  .persona(persona)
                  .interest(interest)
                  .build()
          ));
      personaInterestRepository.saveAll(personaInterestList);

      // charm
      personaCharmRepository.deleteByPersona_Id(personaId);
      List<PersonaCharm> personaCharmList = new ArrayList<>();
      in.getCharmList().forEach(charm -> personaCharmList.add(
          PersonaCharm.builder()
              .persona(persona)
              .name(charm)
              .build()
      ));
      personaCharmRepository.saveAll(personaCharmList);

      return PersonaResponse.toIdOut(personaId);
    }
  }

  @Transactional
  public PersonaResponse.IdOut delete(Long personaId) {

    User user = userService.auth();

    Persona persona = personaRepository.getById(personaId);
    if (user.getId() != persona.getUser().getId()) {
      throw new ForbiddenException();
    } else {
      personaRepository.deleteById(personaId);
      return PersonaResponse.toIdOut(personaId);
    }
  }

  @Transactional
  public PersonaResponse.IdOut setCurrentPersona(Long personaId) {

    User user = userService.auth();

    Persona persona = personaRepository.getById(personaId);
    if (user.getId() != persona.getUser().getId()) {
      throw new ForbiddenException();
    } else {
      user.setPersona(persona);
      return PersonaResponse.toIdOut(personaId);
    }
  }

  @Transactional(readOnly = true)
  public PersonaResponse.Out getCurrentPersona() {
    User user = userService.auth();
    Persona persona = user.getPersona();
    if (persona == null) {
      return null;
    } else {
      return PersonaResponse.toOut(persona);
    }

  }

  public PersonaResponse.DuplicateOut duplicate(String nickname) {
    Optional<Persona> persona = personaRepository.findByNickname(nickname);
    if (persona.isPresent()) {
      return PersonaResponse.toDuplicateOut(true);
    } else {
      return PersonaResponse.toDuplicateOut(false);
    }
  }

  public List<SenseResponse.Out> findSense() {
    List<SenseResponse.Out> outList = senseRepository.findAll().stream().map(SenseResponse::toOut)
        .collect(
            Collectors.toList());
    return outList;
  }


  public List<InterestResponse.Out> findInterest() {
    List<InterestResponse.Out> outList = interestRepository.findAll().stream()
        .map(InterestResponse::toOut).collect(Collectors.toList());
    return outList;
  }


}
