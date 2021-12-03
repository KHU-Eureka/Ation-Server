package com.eureka.ationserver.controller;

import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.dto.persona.PersonaRequest;
import com.eureka.ationserver.repository.UserRepository;
import com.eureka.ationserver.service.PersonaService;
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
@Api(tags = {"Persona"})
@RequiredArgsConstructor
public class PersonaController {
    private final PersonaService personaService;

    private final UserRepository userRepository;

    @PutMapping("/persona/user/{personaId}")
    @ApiOperation(value="활동 페르소나 설정")
    public ResponseEntity setCurrentPersona(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long personaId){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(personaService.setCurrentPersona(user, personaId), null, HttpStatus.OK);

    }

    @GetMapping("/persona/user")
    @ApiOperation(value="활동 페르소나 조회")
    public ResponseEntity getCurrentPersona(@AuthenticationPrincipal UserDetails userDetails){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(personaService.getCurrentPersona(user), null, HttpStatus.OK);

    }

    @GetMapping("/persona")
    @ApiOperation(value="유저의 전체 페르소나 조회")
    public ResponseEntity findAll(@AuthenticationPrincipal UserDetails userDetails){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        return new ResponseEntity(personaService.findAll(user), null, HttpStatus.OK);

    }
    @GetMapping("/persona/{personaId}")
    @ApiOperation(value="특정 페르소나 조회")
    public ResponseEntity find( @PathVariable Long personaId){

        return new ResponseEntity(personaService.find(personaId), null, HttpStatus.OK);

    }

    @GetMapping("/persona/duplicate")
    @ApiOperation(value="페르소나 닉네임 중복확인")
    public ResponseEntity duplicate(@RequestParam(value="nickname") String nickname){
        return new ResponseEntity(personaService.duplicate(nickname),null, HttpStatus.OK);
    }


    @PostMapping("/persona")
    @ApiOperation(value="페르소나 생성")
    public ResponseEntity save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PersonaRequest personaRequest){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(personaService.save(user, personaRequest),null, HttpStatus.CREATED);
    }

    @PostMapping("/persona/image/{personaId}")
    @ApiOperation(value="페르소나 대표이미지")
    public ResponseEntity saveImg(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long personaId,  @RequestParam(value = "profileImg", required = true) MultipartFile profileImg) throws IOException {
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        return new ResponseEntity(personaService.saveImg(user, personaId, profileImg), null, HttpStatus.OK);
    }

    @PutMapping("/persona/{personaId}")
    @ApiOperation(value="페르소나 수정")
    public ResponseEntity update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long personaId,@RequestBody PersonaRequest personaRequest){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(personaService.update(user, personaId, personaRequest), null, HttpStatus.OK);

    }

    @DeleteMapping("/persona/{personaId}")
    @ApiOperation(value="페르소나 삭제")
    public ResponseEntity delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long personaId){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(personaService.delete(user, personaId), null, HttpStatus.OK);

    }


}
