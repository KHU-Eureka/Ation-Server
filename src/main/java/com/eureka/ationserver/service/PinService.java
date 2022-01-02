package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.domain.insight.*;
import com.eureka.ationserver.domain.persona.Persona;
import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.dto.insight.InsightResponse;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
                if(description.length()>255){
                    description = description.substring(0,255);
                }

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

            String icon;
            try {
                icon = document.select("link[rel=apple-touch-icon]").get(0).attr("href");

            } catch (Exception e) {
                icon = insightService.getInsightIconImageDefaultPath();
            }


            Insight insight = Insight.builder()
                    .url(insightPinRequest.getUrl())
                    .title(title)
                    .description(description)
                    .imgPath(imageUrl)
                    .siteName(siteName)
                    .icon(icon)
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
            PinTag pinTag = PinTag.builder()
                    .pin(pin)
                    .name(insight.getInsightMainCategory().getName())
                    .build();
            pinTagRepository.save(pinTag);
            for( InsightCategory insightSubCategory : insight.getInsightSubCategoryList()){
                PinTag tag = PinTag.builder()
                        .pin(pin)
                        .name(insightSubCategory.getInsightSubCategory().getName())
                        .build();
                pinTagRepository.save(tag);

            }
            pinBoard.setImgPath(insight.getImgPath());
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
    public List<PinResponse> findAll(User user, Long personaId){
        Persona persona = personaRepository.getById(personaId);
        if(persona.getUser().getId() != user.getId()){
            throw new ForbiddenException();
        }else{
            List<PinResponse> pinResponseList = pinRepository.findByPinBoard_Persona(persona).stream().map(PinResponse::new).collect(Collectors.toList());
            return pinResponseList;
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
    public Set<PinResponse> search(Long personaId, String keyword){
        Persona persona = personaRepository.getById(personaId);
        Set<Pin> pins1 = pinRepository.findByPinBoard_PersonaAndInsight_TitleContainingOrderByCreatedAtDesc(persona, keyword);
        Set<Pin> pins2 = pinRepository.findByPinTagList_NameContainingOrderByCreatedAtDesc(keyword);
        for(Pin pin : pins2){
            if(pin.getPinBoard().getPersona().getId() == personaId){
                pins1.add(pin);
            }
        }
        Set<PinResponse> pinResponseList = new HashSet<>();
        for(Pin pin : pins1){
            pinResponseList.add(new PinResponse(pin));
        }

        return pinResponseList;
    }

    @Transactional(readOnly = true)
    public List<PinResponse> findByPinBoard(Long pinBoardId){
        PinBoard pinBoard = pinBoardRepository.getById(pinBoardId);
        List<PinResponse> pinResponseList = pinRepository.findByPinBoard(pinBoard).stream().map(PinResponse::new).collect(Collectors.toList());
        return pinResponseList;
    }

    @Value("${eureka.app.publicIp}")
    private String HOST;

    @Value("${server.port}")
    private String PORT;

    @Value("${eureka.app.imagePath}")
    private String IMAGEPATH;

    public String getPinImageDefaultPath(){
        // set file name
        List<String> pathList = new ArrayList<>();

        String fileName = "pin.png";
        String url = "http://"+HOST+":"+PORT+"/api/image?path=";
        String apiPath = url + IMAGEPATH+"pin/" + fileName;
        return apiPath;
    }

    public List<String> getPinImagePath(Long pinId){
        // set file name
        List<String> pathList = new ArrayList<>();

        String fileName = "pin-"+ pinId +".png";
        String url = "http://"+HOST+":"+PORT+"/api/image?path=";
        String apiPath = url +IMAGEPATH+"pin/"+ fileName;

        String path = IMAGEPATH + "pin/"+ fileName;
        pathList.add(apiPath);
        pathList.add(path);
        return pathList;
    }


    @Transactional
    public PinResponse saveImg(Long pinId, MultipartFile pinImg) throws IOException{
        Pin pin = pinRepository.getById(pinId);
        List<String> pathList = getPinImagePath(pinId);
        File file = new File(pathList.get(1));
        pinImg.transferTo(file);
        pin.setPinImgPath(pathList.get(0));

        PinBoard pinBoard = pin.getPinBoard();

        pinBoard.setImgPath(pin.getPinImgPath());

        return new PinResponse(pin);
    }
}
