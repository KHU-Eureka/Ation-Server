package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.persona.PersonaRequest;
import com.eureka.ationserver.service.PersonaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Api(tags = {"Persona"})
@RequiredArgsConstructor
public class PersonaController {

  private final PersonaService personaService;

  @PutMapping("/persona/user/{personaId}")
  @ApiOperation(value = "활동 페르소나 설정")
  public ResponseEntity setCurrentPersona(@PathVariable Long personaId) {
    return new ResponseEntity(personaService.setCurrentPersona(personaId), null, HttpStatus.OK);

  }

  @GetMapping("/persona/user")
  @ApiOperation(value = "활동 페르소나 조회")
  public ResponseEntity getCurrentPersona() {
    return new ResponseEntity(personaService.getCurrentPersona(), null, HttpStatus.OK);

  }

  @GetMapping("/persona")
  @ApiOperation(value = "유저의 전체 페르소나 조회")
  public ResponseEntity findAll() {

    return new ResponseEntity(personaService.findAll(), null, HttpStatus.OK);

  }

  @GetMapping("/persona/{personaId}")
  @ApiOperation(value = "특정 페르소나 조회")
  public ResponseEntity find(@PathVariable Long personaId) {

    return new ResponseEntity(personaService.find(personaId), null, HttpStatus.OK);

  }

  @GetMapping("/persona/duplicate")
  @ApiOperation(value = "페르소나 닉네임 중복확인")
  public ResponseEntity duplicate(@RequestParam(value = "nickname") String nickname) {
    return new ResponseEntity(personaService.duplicate(nickname), null, HttpStatus.OK);
  }


  @PostMapping("/persona")
  @ApiOperation(value = "페르소나 생성")
  public ResponseEntity save(@RequestBody PersonaRequest personaRequest) {
    return new ResponseEntity(personaService.save(personaRequest), null, HttpStatus.CREATED);
  }

  @PostMapping("/persona/image/{personaId}")
  @ApiOperation(value = "페르소나 대표이미지")
  public ResponseEntity saveImg(@PathVariable Long personaId,
      @RequestParam(value = "profileImg", required = true) MultipartFile profileImg)
      throws IOException {

    return new ResponseEntity(personaService.saveImg(personaId, profileImg), null, HttpStatus.OK);
  }

  @PutMapping("/persona/{personaId}")
  @ApiOperation(value = "페르소나 수정")
  public ResponseEntity update(@PathVariable Long personaId,
      @RequestBody PersonaRequest personaRequest) {
    return new ResponseEntity(personaService.update(personaId, personaRequest), null,
        HttpStatus.OK);

  }

  @DeleteMapping("/persona/{personaId}")
  @ApiOperation(value = "페르소나 삭제")
  public ResponseEntity delete(@PathVariable Long personaId) {
    return new ResponseEntity(personaService.delete(personaId), null, HttpStatus.OK);

  }


}
