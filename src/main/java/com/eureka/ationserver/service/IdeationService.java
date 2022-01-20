package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.dto.ideation.IdeationRequest;
import com.eureka.ationserver.dto.ideation.IdeationResponse;
import com.eureka.ationserver.model.ideation.Ideation;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.ideation.IdeationRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class IdeationService {

  private final IdeationRepository ideationRepository;
  private final PersonaRepository personaRepository;

  @Transactional
  public Long save(User user, IdeationRequest ideationRequest) {
    Persona persona = personaRepository.findById(ideationRequest.getPersonaId()).get();
    if (user.getId() != persona.getUser().getId()) {
      throw new ForbiddenException();
    } else {
      String defaultPath = getIdeationImageDefaultPath();
      Ideation ideation = ideationRequest.toEntity(persona, defaultPath);
      Ideation saved = ideationRepository.save(ideation);
      return saved.getId();
    }

  }

  @Transactional
  public IdeationResponse saveImg(User user, Long ideationId, MultipartFile ideationImg)
      throws IOException {
    Ideation ideation = ideationRepository.getById(ideationId);
    if(user.getId() != ideation.getPersona().getUser().getId()){
      throw new ForbiddenException();
    }else{
      List<String> pathList = getIdeationImagePath(ideationId);
      File file = new File(pathList.get(1));
      ideationImg.transferTo(file);
      ideation.setImgPath(pathList.get(0));
      return new IdeationResponse(ideation);
    }

  }

  @Transactional(readOnly = true)
  public List<IdeationResponse> findAll(User user, Long personaId){
    Persona persona = personaRepository.findById(personaId).get();
    if(user.getId() != persona.getUser().getId()){
      throw new ForbiddenException();
    }else{
      List<Ideation> ideationList = ideationRepository.findByPersona_Id(personaId);
      List<IdeationResponse> ideationResponseList = new ArrayList<>();
      for(Ideation ideation : ideationList){
        ideationResponseList.add(new IdeationResponse(ideation));
      }
      return ideationResponseList;
    }
  }

  @Transactional
  public Long update(User user, Long ideationId, IdeationRequest ideationRequest){
    Ideation ideation = ideationRepository.getById(ideationId);
    if(user.getId() != ideation.getPersona().getUser().getId()){
      throw new ForbiddenException();
    }else{
      ideation.update(ideationRequest);
      return ideationId;
    }
  }

  @Transactional
  public Long delete(User user, Long ideationId){
    Ideation ideation = ideationRepository.getById(ideationId);
    if(user.getId() != ideation.getPersona().getUser().getId()){
      throw new ForbiddenException();
    }else{
      ideationRepository.deleteById(ideationId);
      return ideationId;
    }
  }


  @Value("${eureka.app.publicIp}")
  private String HOST;

  @Value("${server.port}")
  private String PORT;

  @Value("${eureka.app.imagePath}")
  private String IMAGEPATH;

  private String getIdeationImageDefaultPath() {
    // set file name
    List<String> pathList = new ArrayList<>();

    String fileName = "ideation.png";
    String url = "http://" + HOST + ":" + PORT + "/api/image?path=";
    String apiPath = url + IMAGEPATH + "ideation/" + fileName;
    return apiPath;
  }

  private List<String> getIdeationImagePath(Long pinBoardId) {
    // set file name
    List<String> pathList = new ArrayList<>();

    String fileName = "pinBoard-" + pinBoardId + ".png";
    String url = "http://" + HOST + ":" + PORT + "/api/image?path=";
    String apiPath = url + IMAGEPATH + "ideation/" + fileName;

    String path = IMAGEPATH + "ideation/" + fileName;
    pathList.add(apiPath);
    pathList.add(path);
    return pathList;
  }


}
