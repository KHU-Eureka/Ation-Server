package com.eureka.ationserver.service;


import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.domain.insight.PinBoard;
import com.eureka.ationserver.domain.persona.Persona;
import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.dto.pin.PinResponse;
import com.eureka.ationserver.dto.pinBoard.PinBoardRequest;
import com.eureka.ationserver.dto.pinBoard.PinBoardResponse;
import com.eureka.ationserver.dto.pinBoard.PinBoardUpdateRequest;
import com.eureka.ationserver.repository.insight.PinBoardRepository;
import com.eureka.ationserver.repository.insight.PinRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PinBoardService {

    private final PinBoardRepository pinBoardRepository;
    private final PersonaRepository personaRepository;
    private final PinRepository pinRepository;

    @Transactional
    public Long save(User user, PinBoardRequest pinBoardRequest){
        Persona persona = personaRepository.findById(pinBoardRequest.getPersonaId()).get();
        if(user.getId() != persona.getUser().getId()){
            throw new ForbiddenException();
        }else{
            String defaultPath = getPinBoardImageDefaultPath();
            PinBoard pinBoard = pinBoardRequest.toEntity(persona, defaultPath);
            PinBoard saved = pinBoardRepository.save(pinBoard);
            return saved.getId();
        }
    }

    @Value("${eureka.app.publicIp")
    private String HOST;

    @Value("${server.port}")
    private String PORT;

    @Value("${eureka.app.imagePath}")
    private String IMAGEPATH;

    private String getPinBoardImageDefaultPath(){
        // set file name
                List<String> pathList = new ArrayList<>();

        String fileName = "pinBoard.png";
        String url = "http://"+HOST+":"+PORT+"/api/image?path=";
        String apiPath = url + IMAGEPATH+"pinboard/" + fileName;
        return apiPath;
    }

    private List<String> getPinBoardImagePath(Long pinBoardId){
        // set file name
        List<String> pathList = new ArrayList<>();

        String fileName = "pinBoard-"+ pinBoardId +".png";
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
        if(user.getId() != pinBoard.getPersona().getUser().getId()) {
            throw new ForbiddenException();
        }else{
            List<String> pathList = getPinBoardImagePath(pinBoardId);
            File file = new File(pathList.get(1));
            pinBoardImg.transferTo(file);
            pinBoard.setImgPath(pathList.get(0));
            return new PinBoardResponse(pinBoard, getCountOfPin(pinBoard));
        }
    }

    @Transactional(readOnly = true)
    public List<PinBoardResponse> findAll(User user, Long personaId){
        Persona persona = personaRepository.findById(personaId).get();
        if(user.getId() != persona.getUser().getId()){
            throw new ForbiddenException();
        }else{
            List<PinBoard> pinBoardList = pinBoardRepository.findByPersona_Id(personaId);
            List<PinBoardResponse> pinBoardResponseList = new ArrayList<>();
            for(PinBoard pinBoard : pinBoardList){
                pinBoardResponseList.add(new PinBoardResponse(pinBoard, getCountOfPin(pinBoard)));

            }
            return pinBoardResponseList;
        }
    }

    @Transactional
    public Long update(User user, Long pinBoardId, PinBoardUpdateRequest pinBoardRequest){
        PinBoard pinBoard = pinBoardRepository.getById(pinBoardId);
        if(user.getId() != pinBoard.getPersona().getUser().getId()) {
            throw new ForbiddenException();
        }else {

            pinBoard.update(pinBoardRequest.getName());
            return pinBoardId;
        }
    }

    @Transactional
    public Long delete(User user, Long pinBoardId){
        PinBoard pinBoard = pinBoardRepository.getById(pinBoardId);
        if(user.getId() != pinBoard.getPersona().getUser().getId()) {
            throw new ForbiddenException();
        }else {
            pinBoardRepository.deleteById(pinBoardId);
            return pinBoardId;
        }

    }

    public Long getCountOfPin(PinBoard pinBoard){
        return pinRepository.countByPinBoard(pinBoard);
    }

}
