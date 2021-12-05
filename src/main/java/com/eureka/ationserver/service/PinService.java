package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.domain.insight.*;
import com.eureka.ationserver.domain.persona.Persona;
import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.dto.pin.InsightPinRequest;
import com.eureka.ationserver.dto.pin.PinRequest;
import com.eureka.ationserver.dto.pin.PinResponse;
import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.dto.pin.PinUpdateRequest;
import com.eureka.ationserver.repository.insight.PinRepository;
import com.eureka.ationserver.repository.insight.InsightRepository;
import com.eureka.ationserver.repository.insight.PinBoardRepository;
import com.eureka.ationserver.repository.insight.PinTagRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    public Long saveNewPin(User user, InsightPinRequest insightPinRequest) throws IOException {

        PinBoard pinBoard = pinBoardRepository.getById(insightPinRequest.getPinBoardId());

        if (user.getId() != pinBoard.getPersona().getUser().getId()) {
            throw new ForbiddenException();
        } else {
            // save insgiht
            String crawlingUrl = insightPinRequest.getUrl();
            Document document = Jsoup.connect(crawlingUrl).get();

            String title = document.select("meta[property=og:title]").first().attr("content");

            String description;
            try {
                description = document.select("meta[property=og:description]").get(0).attr("content");

            } catch (Exception e) {
                description = null;
            }

            String imageUrl;
            try {
                imageUrl = document.select("meta[property=og:image]").get(0).attr("content");

            } catch (Exception e) {
                imageUrl = insightService.getInsightImageDefaultPath();
            }

            String siteName;
            try {
                siteName = document.select("meta[property=og:site_name]").get(0).attr("content");

            } catch (Exception e) {
                siteName = "-";
            }


            Insight insight = Insight.builder()
                    .url(insightPinRequest.getUrl())
                    .title(title)
                    .description(description)
                    .imgPath(imageUrl)
                    .siteName(siteName)
                    .insightMainCategory(null)
                    .insightSubCategory(null)
                    .open(false)
                    .build();
            insightRepository.save(insight);


            // save InsightPin
            Pin pin = Pin.builder()
                    .pinBoard(pinBoard)
                    .insight(insight)
                    .build();
            Pin saved = pinRepository.save(pin);

            // save Pin tag
            for(String name : insightPinRequest.getTagList()){
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
    public Long pinUp(User user, PinRequest pinRequest){
        PinBoard pinBoard = pinBoardRepository.getById(pinRequest.getPinBoardId());
        if(user.getId() != pinBoard.getPersona().getUser().getId()){
            throw new ForbiddenException();
        }else{
            Insight insight = insightRepository.getById(pinRequest.getInsightId());
            Pin pin = pinRequest.toEnitity(pinBoard, insight);
            Pin saved = pinRepository.save(pin);
            // Copy Tags
            for(InsightTag insightTag : insight.getInsightTagList()){
                PinTag pinTag = PinTag.builder()
                        .pin(pin)
                        .name(insightTag.getName())
                        .build();
                pinTagRepository.save(pinTag);
            }

            return saved.getId();
        }

    }

    @Transactional
    public Long update(User user, PinUpdateRequest pinUpdateRequest, Long pinId){
        Pin pin = pinRepository.getById(pinId);
        if(user.getId() != pin.getPinBoard().getPersona().getUser().getId()){
            throw new ForbiddenException();
        }else{
            PinBoard pinBoard = pinBoardRepository.getById(pinUpdateRequest.getPinBoardId());
            if(user.getId() != pinBoard.getPersona().getUser().getId()){
                throw new ForbiddenException();
            }else{
                pin.setPinBoard(pinBoard);

                pinTagRepository.deleteByPin_Id(pinId);
                for(String name : pinUpdateRequest.getTagList()){
                    PinTag pinTag = PinTag.builder()
                            .pin(pin)
                            .name(name)
                            .build();
                    pinTagRepository.save(pinTag);
                }
                return pinId;

            }
        }
    }

    @Transactional
    public Long delete(User user, Long pinId){
        Pin pin = pinRepository.getById(pinId);
        if(user.getId() != pin.getPinBoard().getPersona().getUser().getId()){
            throw new ForbiddenException();
        }else{
            pinRepository.deleteById(pinId);
            return pinId;
        }
    }

    @Transactional(readOnly = true)
    public PinResponse find(User user, Long pinId){
        Pin pin = pinRepository.getById(pinId);
        if(user.getId() != pin.getPinBoard().getPersona().getUser().getId()){
            throw new ForbiddenException();
        }else {
            return new PinResponse(pin);
        }
    }

    @Transactional(readOnly = true)
    public List<PinResponse> search(Long personaId, String keyword){
        Persona persona = personaRepository.getById(personaId);
        List<PinResponse> pinResponseList = pinRepository.findByPinBoard_PersonaAndInsight_TitleContaining(persona, keyword).stream().map(PinResponse::new).collect(Collectors.toList());
        return pinResponseList;
    }

    @Transactional(readOnly = true)
    public List<PinResponse> findByPinBoard(Long pinBoardId){
        System.out.println(pinBoardId);
        PinBoard pinBoard = pinBoardRepository.getById(pinBoardId);
        List<PinResponse> pinResponseList = pinRepository.findByPinBoard(pinBoard).stream().map(PinResponse::new).collect(Collectors.toList());
        return pinResponseList;
    }

}
