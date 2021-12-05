package com.eureka.ationserver.controller;

import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.dto.pinBoard.PinBoardRequest;
import com.eureka.ationserver.repository.UserRepository;
import com.eureka.ationserver.service.PinBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@Api(tags = {"Pin Board"})
@RequiredArgsConstructor
public class PinBoardController {

    private final PinBoardService pinBoardService;
    private final UserRepository userRepository;

    @PostMapping("/pin-board")
    @ApiOperation(value="핀보드 생성")
    public ResponseEntity save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PinBoardRequest pinBoardRequest){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinBoardService.save(user, pinBoardRequest), null, HttpStatus.CREATED);

    }

    @PostMapping("/pin-board/image/{pinBoardId}")
    @ApiOperation(value="핀보드 이미지 설정")
    public ResponseEntity saveImg(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long pinBoardId, @RequestParam(value = "pinBoardImg", required = true) MultipartFile pinBoardImg) throws IOException {
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinBoardService.saveImg(user, pinBoardId, pinBoardImg), null, HttpStatus.OK);
    }

    @GetMapping("/pin-board/user")
    @ApiOperation(value="유저의 페르소나별 핀보드 조회")
    public ResponseEntity findAll(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value="personaId") Long personaId){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinBoardService.findAll(user, personaId), null, HttpStatus.OK);

    }

    @PutMapping("/pin-board/{pinBoardId}")
    @ApiOperation(value="핀보드 수정")
    public ResponseEntity update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long pinBoardId, @RequestBody PinBoardRequest pinBoardRequest){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinBoardService.update(user, pinBoardId, pinBoardRequest), null, HttpStatus.OK);
    }

    @DeleteMapping("/pin-board/{pinBoardId}")
    @ApiOperation(value="핀보드 삭제")
    public ResponseEntity delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long pinBoardId){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinBoardService.delete(user, pinBoardId), null, HttpStatus.OK);


    }
}
