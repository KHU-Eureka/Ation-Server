package com.eureka.ationserver.service;

import com.eureka.ationserver.dto.ideation.IdeationRequest;
import com.eureka.ationserver.dto.ideation.IdeationResponse;
import com.eureka.ationserver.model.ideation.Ideation;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.repository.ideation.IdeationRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import com.eureka.ationserver.utils.image.ImageUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class IdeationService {

  private final IdeationRepository ideationRepository;
  private final PersonaRepository personaRepository;

  @Transactional
  public IdeationResponse.IdOut save(IdeationRequest.In in) {
    Persona persona = personaRepository.findById(in.getPersonaId()).get();

    String defaultPath = ImageUtil.getDefaultImagePath(Ideation.IDEATION_PREFIX);
    Ideation ideation = in.toIdeation(persona, defaultPath);

    return IdeationResponse.toIdOut(ideationRepository.save(ideation).getId());

  }

  @Transactional
  public IdeationResponse.IdOut saveImg(Long ideationId, MultipartFile ideationImg)
      throws IOException {
    Ideation ideation = ideationRepository.getById(ideationId);
    List<String> pathList = ImageUtil.getImagePath(Ideation.IDEATION_PREFIX, ideationId);
    File file = new File(pathList.get(1));
    ideationImg.transferTo(file);
    ideation.setImgPath(pathList.get(0));
    return IdeationResponse.toIdOut(ideationId);


  }

  @Transactional(readOnly = true)
  public List<IdeationResponse.Out> findAll(Long personaId) {
    Persona persona = personaRepository.findById(personaId).get();
    List<IdeationResponse.Out> outList = new ArrayList<>();
    ideationRepository.findByPersona_Id(personaId).forEach(ideation ->
        outList.add(IdeationResponse.toOut(ideation))
    );

    return outList;

  }

  @Transactional(readOnly = true)
  public IdeationResponse.Out find(Long ideationId) {
    return IdeationResponse.toOut(ideationRepository.getById(ideationId));
  }

  @Transactional
  public IdeationResponse.IdOut updateTitle(Long ideationId, IdeationRequest.TitleIn in) {
    Ideation ideation = ideationRepository.getById(ideationId);
    ideation.setTitle(in.getTitle());
    return IdeationResponse.toIdOut(ideationId);

  }

  @Transactional
  public IdeationResponse.IdOut updateWhiteboard(Long ideationId, IdeationRequest.WhiteboardIn in) {
    Ideation ideation = ideationRepository.getById(ideationId);
    ideation.setWhiteboard(in.getWhiteboard());
    return IdeationResponse.toIdOut(ideationId);
  }

  @Transactional
  public IdeationResponse.IdOut delete(Long ideationId) {
    ideationRepository.deleteById(ideationId);
    return IdeationResponse.toIdOut(ideationId);

  }


}
