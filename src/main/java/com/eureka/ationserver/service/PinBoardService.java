package com.eureka.ationserver.service;


import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.domain.insight.PinBoard;
import com.eureka.ationserver.domain.persona.Persona;
import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.dto.insight.PinBoardRequest;
import com.eureka.ationserver.dto.insight.PinBoardResponse;
import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import com.eureka.ationserver.repository.insight.PinBoardRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import com.eureka.ationserver.repository.persona.PersonaSenseReposotiry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PinBoardService {

    private final PinBoardRepository pinBoardRepository;
    private final PersonaRepository personaRepository;
    @Transactional
    public PinBoardResponse save(User user, PinBoardRequest pinBoardRequest){
        Persona persona = personaRepository.findById(pinBoardRequest.getPersonaId()).get();
        String defaultPath = getPinBoardImageDefaultPath();
        PinBoard pinBoard = pinBoardRequest.toEntity(user, persona, defaultPath);
        PinBoard saved = pinBoardRepository.save(pinBoard);
        return new PinBoardResponse(saved , new PersonaSimpleResponse(persona));

    }

    @Value("${server.address}")
    private String HOST;

    @Value("${server.port}")
    private String PORT;

    @Value("${eureka.app.imagePath}")
    private String IMAGEPATH;

    private String getPinBoardImageDefaultPath(){
        // set file name
                List<String> pathList = new ArrayList<>();

        String fileName = "pinboard.png";
        String url = "http://"+HOST+":"+PORT+"/api/image?path=";
        String apiPath = url + IMAGEPATH+"pinboard/" + fileName;
        return apiPath;
    }

    private List<String> getPinBoardImagePath(Long pinBoardId){
        // set file name

        List<String> pathList = new ArrayList<>();

        String fileName = "persona-"+ pinBoardId +".png";
        String url = "http://"+HOST+":"+PORT+"/api/image?path=";
        String apiPath = url +IMAGEPATH+"pinboard/"+ fileName;

        String path = IMAGEPATH + "pinboard/"+ fileName;
        pathList.add(apiPath);
        pathList.add(path);
        return pathList;
    }

    @Transactional
    public PinBoardResponse saveImg(User user, Long pinBoardId, MultipartFile pinBoardImg) throws IOException{
        PinBoard pinBoard = pinBoardRepository.getById(pinBoardId);
        if(user.getId() != pinBoard.getUser().getId()) {
            throw new ForbiddenException();
        }else{
            List<String> pathList = getPinBoardImagePath(pinBoardId);
            File file = new File(pathList.get(1));
            pinBoardImg.transferTo(file);
            pinBoard.setImgPath(pathList.get(0));
            return new PinBoardResponse(pinBoard, new PersonaSimpleResponse(pinBoard.getPersona()));
        }
    }


}
