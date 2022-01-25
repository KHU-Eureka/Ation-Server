package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.model.insight.*;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.dto.pin.InsightPinRequest;
import com.eureka.ationserver.dto.pin.PinRequest;
import com.eureka.ationserver.dto.pin.PinResponse;
import com.eureka.ationserver.dto.pin.PinUpdateRequest;
import com.eureka.ationserver.repository.insight.PinRepository;
import com.eureka.ationserver.repository.insight.InsightRepository;
import com.eureka.ationserver.repository.insight.PinBoardRepository;
import com.eureka.ationserver.repository.insight.PinTagRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import com.eureka.ationserver.utils.image.ImageUtil;
import com.eureka.ationserver.utils.parse.Parse;
import com.eureka.ationserver.utils.parse.ParseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PinService {

  private final InsightRepository insightRepository;
  private final PinBoardRepository pinBoardRepository;
  private final PinTagRepository pinTagRepository;
  private final PinRepository pinRepository;
  private final InsightService insightService;
  private final PersonaRepository personaRepository;


  @Transactional
  public Long saveNewPin(User user, InsightPinRequest insightPinRequest) throws Exception {

    PinBoard pinBoard = pinBoardRepository.getById(insightPinRequest.getPinBoardId());

    if (user.getId() != pinBoard.getPersona().getUser().getId()) {
      throw new ForbiddenException();
    } else {

      Parse parse = ParseUtil.parse(Pin.PIN_PREFIX, insightPinRequest.getUrl());

      Insight insight = Insight.builder()
          .url(insightPinRequest.getUrl())
          .title(parse.getTitle())
          .description(parse.getDescription())
          .imgPath(parse.getImageUrl())
          .siteName(parse.getSiteName())
          .icon(parse.getIcon())
          .insightMainCategory(null)
          .insightSubCategoryList(null)
          .open(false)
          .build();

      insightRepository.save(insight);
      pinBoard.setImgPath(insight.getImgPath());

      // save InsightPin
      Pin pin = Pin.builder()
          .pinBoard(pinBoard)
          .insight(insight)
          .pinImgPath(insight.getImgPath())
          .build();
      Pin saved = pinRepository.save(pin);

      // save Pin tag
      for (String name : insightPinRequest.getTagList()) {
        PinTag pinTag = PinTag.builder()
            .pin(pin)
            .name(name)
            .build();
        pinTagRepository.save(pinTag);
      }

      return saved.getId();

    }
  }


  @Transactional
  public Long pinUp(User user, PinRequest pinRequest) {
    PinBoard pinBoard = pinBoardRepository.getById(pinRequest.getPinBoardId());
    if (user.getId() != pinBoard.getPersona().getUser().getId()) {
      throw new ForbiddenException();
    } else {
      Insight insight = insightRepository.getById(pinRequest.getInsightId());
      Pin pin = pinRequest.toEnitity(pinBoard, insight);
      Pin saved = pinRepository.save(pin);
      // Copy Tags
      PinTag pinTag = PinTag.builder()
          .pin(pin)
          .name(insight.getInsightMainCategory().getName())
          .build();
      pinTagRepository.save(pinTag);
      for (InsightSubCategory insightSubCategory : insight.getInsightSubCategoryList()) {
        PinTag tag = PinTag.builder()
            .pin(pin)
            .name(insightSubCategory.getSubCategory().getName())
            .build();
        pinTagRepository.save(tag);

      }
      pinBoard.setImgPath(insight.getImgPath());
      return saved.getId();
    }

  }

  @Transactional
  public Long update(User user, PinUpdateRequest pinUpdateRequest, Long pinId) {
    Pin pin = pinRepository.getById(pinId);
    PinBoard orgPinBoard = pin.getPinBoard();
    if (user.getId() != pin.getPinBoard().getPersona().getUser().getId()) {
      throw new ForbiddenException();
    } else {
      PinBoard pinBoard = pinBoardRepository.getById(pinUpdateRequest.getPinBoardId());
      if (user.getId() != pinBoard.getPersona().getUser().getId()) {
        throw new ForbiddenException();
      } else {

        if (orgPinBoard.getId() != pinBoard.getId()) {
          pinTagRepository.deleteByPin_Id(pinId);
          for (String name : pinUpdateRequest.getTagList()) {
            PinTag pinTag = PinTag.builder()
                .pin(pin)
                .name(name)
                .build();
            pinTagRepository.save(pinTag);
          }
        }
        pin.setPinBoard(pinBoard);

        // change pinBoard image
        List<Pin> orgList = pinRepository.findByPinBoardOrderByModifiedAtDesc(orgPinBoard);
        if (orgList.size() == 0) {
          orgPinBoard.setImgPath(ImageUtil.getDefaultImagePath(PinBoard.PINBOARD_PREFIX));
        } else {
          orgPinBoard.setImgPath(orgList.get(0).getPinImgPath());
        }
        pinBoard.setImgPath(pin.getPinImgPath());
        return pinId;

      }
    }
  }

  @Transactional
  public Long delete(User user, Long pinId) {
    Pin pin = pinRepository.getById(pinId);
    if (user.getId() != pin.getPinBoard().getPersona().getUser().getId()) {
      throw new ForbiddenException();
    } else {
      PinBoard orgPinBoard = pin.getPinBoard();
      pinRepository.deleteById(pinId);

      // change pinBoard image
      List<Pin> orgList = pinRepository.findByPinBoardOrderByModifiedAtDesc(orgPinBoard);
      if (orgList.size() == 0) {
        orgPinBoard.setImgPath(ImageUtil.getDefaultImagePath(PinBoard.PINBOARD_PREFIX));
      } else {
        orgPinBoard.setImgPath(orgList.get(0).getPinImgPath());
      }
      return pinId;
    }
  }

  @Transactional(readOnly = true)
  public List<PinResponse> findAll(User user, Long personaId) {
    Persona persona = personaRepository.getById(personaId);
    if (persona.getUser().getId() != user.getId()) {
      throw new ForbiddenException();
    } else {
      List<PinResponse> pinResponseList = pinRepository.findByPinBoard_Persona(persona).stream()
          .map(PinResponse::new).collect(Collectors.toList());
      return pinResponseList;
    }

  }

  @Transactional(readOnly = true)
  public PinResponse find(User user, Long pinId) {
    Pin pin = pinRepository.getById(pinId);
    if (user.getId() != pin.getPinBoard().getPersona().getUser().getId()) {
      throw new ForbiddenException();
    } else {
      return new PinResponse(pin);
    }
  }

  @Transactional(readOnly = true)
  public Set<PinResponse> search(User user, Long personaId, String keyword) {
    Persona persona = personaRepository.getById(personaId);
    if (persona.getUser().getId() != user.getId()) {
      throw new ForbiddenException();
    }
    Set<Pin> pins1 = pinRepository.findByPinBoard_PersonaAndInsight_TitleContainingOrderByCreatedAtDesc(
        persona, keyword);
    Set<Pin> pins2 = pinRepository.findByPinTagList_NameContainingOrderByCreatedAtDesc(keyword);
    for (Pin pin : pins2) {
      if (pin.getPinBoard().getPersona().getId() == personaId) {
        pins1.add(pin);
      }
    }
    Set<PinResponse> pinResponseList = new HashSet<>();
    for (Pin pin : pins1) {
      pinResponseList.add(new PinResponse(pin));
    }

    return pinResponseList;
  }

  @Transactional(readOnly = true)
  public List<PinResponse> findByPinBoard(User user, Long pinBoardId) {
    PinBoard pinBoard = pinBoardRepository.getById(pinBoardId);
    if (pinBoard.getPersona().getUser().getId() != user.getId()) {
      throw new ForbiddenException();
    }
    List<PinResponse> pinResponseList = pinRepository.findByPinBoard(pinBoard).stream()
        .map(PinResponse::new).collect(Collectors.toList());
    return pinResponseList;
  }


  @Transactional
  public PinResponse saveImg(Long pinId, MultipartFile pinImg) throws IOException {
    Pin pin = pinRepository.getById(pinId);
    List<String> pathList = ImageUtil.getImagePath(Pin.PIN_PREFIX, pinId);
    File file = new File(pathList.get(1));
    pinImg.transferTo(file);
    pin.setPinImgPath(pathList.get(0));

    PinBoard pinBoard = pin.getPinBoard();

    pinBoard.setImgPath(pin.getPinImgPath());

    return new PinResponse(pin);
  }
}
