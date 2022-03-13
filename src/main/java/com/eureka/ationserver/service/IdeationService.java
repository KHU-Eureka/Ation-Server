package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.dto.ideation.IdeationRequest;
import com.eureka.ationserver.dto.ideation.IdeationResponse;
import com.eureka.ationserver.dto.ideation.IdeationTitleRequest;
import com.eureka.ationserver.dto.whiteboard.WhiteboardRequest;
import com.eureka.ationserver.model.ideation.Ideation;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.ideation.IdeationRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import com.eureka.ationserver.utils.image.ImageUtil;
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
  public Long save(IdeationRequest ideationRequest) {
    Persona persona = personaRepository.findById(ideationRequest.getPersonaId()).get();

      String defaultPath = ImageUtil.getDefaultImagePath(Ideation.IDEATION_PREFIX);
      Ideation ideation = ideationRequest.toEntity(persona, defaultPath);
      Ideation saved = ideationRepository.save(ideation);
      return saved.getId();


  }

  @Transactional
  public IdeationResponse saveImg(Long ideationId, MultipartFile ideationImg)
      throws IOException {
    Ideation ideation = ideationRepository.getById(ideationId);
      List<String> pathList = ImageUtil.getImagePath(Ideation.IDEATION_PREFIX,ideationId);
      File file = new File(pathList.get(1));
      ideationImg.transferTo(file);
      ideation.setImgPath(pathList.get(0));
      return new IdeationResponse(ideation);


  }

  @Transactional(readOnly = true)
  public List<IdeationResponse> findAll(Long personaId){
    Persona persona = personaRepository.findById(personaId).get();
    List<Ideation> ideationList = ideationRepository.findByPersona_Id(personaId);
    List<IdeationResponse> ideationResponseList = new ArrayList<>();
    for(Ideation ideation : ideationList){
      ideationResponseList.add(new IdeationResponse(ideation));
    }
    return ideationResponseList;

  }

  @Transactional(readOnly = true)
  public IdeationResponse find(Long ideationId){
    return new IdeationResponse(ideationRepository.getById(ideationId));
  }

  @Transactional
  public Long updateTitle(Long ideationId, IdeationTitleRequest ideationTitleRequest){
    Ideation ideation = ideationRepository.getById(ideationId);
    ideation.setTitle(ideationTitleRequest.getTitle());
    return ideationId;

  }

  @Transactional
  public Long updateWhiteboard(Long ideationId, WhiteboardRequest whiteboardRequest){
    Ideation ideation = ideationRepository.getById(ideationId);
    ideation.setWhiteboard(whiteboardRequest.getWhiteboard());
    return ideationId;
  }

  @Transactional
  public Long delete(Long ideationId){
    ideationRepository.deleteById(ideationId);
    return ideationId;

  }


}
