package com.eureka.ationserver.service;


import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.dto.pinBoard.PinBoardRequest;
import com.eureka.ationserver.dto.pinBoard.PinBoardResponse;
import com.eureka.ationserver.dto.pinBoard.PinBoardUpdateRequest;
import com.eureka.ationserver.model.insight.PinBoard;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.insight.PinBoardRepository;
import com.eureka.ationserver.repository.insight.PinRepository;
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
public class PinBoardService {

    private final PinBoardRepository pinBoardRepository;
    private final PersonaRepository personaRepository;
    private final PinRepository pinRepository;
    private final AuthService authService;

    @Transactional
    public Long save(PinBoardRequest pinBoardRequest) {
        User user = authService.auth();
        Persona persona = personaRepository.findById(pinBoardRequest.getPersonaId()).get();
        if (user.getId() != persona.getUser().getId()) {
            throw new ForbiddenException();
        } else {
            String defaultPath = ImageUtil.getDefaultImagePath(PinBoard.PINBOARD_PREFIX);
            PinBoard pinBoard = pinBoardRequest.toEntity(persona, defaultPath);
            PinBoard saved = pinBoardRepository.save(pinBoard);
            return saved.getId();
        }
    }


    @Transactional
    public PinBoardResponse saveImg(Long pinBoardId, MultipartFile pinBoardImg) throws IOException {
        User user = authService.auth();

        PinBoard pinBoard = pinBoardRepository.getById(pinBoardId);
        if (user.getId() != pinBoard.getPersona().getUser().getId()) {
            throw new ForbiddenException();
        } else {
            List<String> pathList = ImageUtil.getImagePath(PinBoard.PINBOARD_PREFIX, pinBoardId);
            File file = new File(pathList.get(1));
            pinBoardImg.transferTo(file);
            pinBoard.setImgPath(pathList.get(0));
            return new PinBoardResponse(pinBoard, getCountOfPin(pinBoard));
        }
    }

    @Transactional(readOnly = true)
    public List<PinBoardResponse> findAll(Long personaId) {
        User user = authService.auth();

        Persona persona = personaRepository.findById(personaId).get();
        if (user.getId() != persona.getUser().getId()) {
            throw new ForbiddenException();
        } else {
            List<PinBoard> pinBoardList = pinBoardRepository.findByPersona_Id(personaId);
            List<PinBoardResponse> pinBoardResponseList = new ArrayList<>();
            for (PinBoard pinBoard : pinBoardList) {
                pinBoardResponseList.add(new PinBoardResponse(pinBoard, getCountOfPin(pinBoard)));

            }
            return pinBoardResponseList;
        }
    }

    @Transactional
    public Long update(Long pinBoardId, PinBoardUpdateRequest pinBoardRequest) {
        User user = authService.auth();

        PinBoard pinBoard = pinBoardRepository.getById(pinBoardId);
        if (user.getId() != pinBoard.getPersona().getUser().getId()) {
            throw new ForbiddenException();
        } else {

            pinBoard.update(pinBoardRequest.getName());
            return pinBoardId;
        }
    }

    @Transactional
    public Long delete(Long pinBoardId) {
        User user = authService.auth();

        PinBoard pinBoard = pinBoardRepository.getById(pinBoardId);
        if (user.getId() != pinBoard.getPersona().getUser().getId()) {
            throw new ForbiddenException();
        } else {
            pinBoardRepository.deleteById(pinBoardId);
            return pinBoardId;
        }

    }

    public Long getCountOfPin(PinBoard pinBoard){
        return pinRepository.countByPinBoard(pinBoard);
    }

}
