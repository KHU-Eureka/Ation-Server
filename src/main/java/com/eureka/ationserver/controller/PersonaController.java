package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.persona.PersonaRequest;
import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.dto.persona.category.InterestResponse;
import com.eureka.ationserver.dto.persona.category.SenseResponse;
import com.eureka.ationserver.service.PersonaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

  @PostMapping("/persona")
  @ApiOperation(value = "페르소나 생성")
  public PersonaResponse.IdOut save(@RequestBody PersonaRequest.In in) {
    return personaService.save(in);
  }

  @GetMapping("/persona/{personaId}")
  @ApiOperation(value = "페르소나 조회")
  public PersonaResponse.Out find(@PathVariable Long personaId) {
    return personaService.find(personaId);

  }

  @PutMapping("/persona/{personaId}")
  @ApiOperation(value = "페르소나 수정")
  public PersonaResponse.IdOut update(@PathVariable Long personaId,
      @RequestBody PersonaRequest.In in) {
    return personaService.update(personaId, in);
  }

  @DeleteMapping("/persona/{personaId}")
  @ApiOperation(value = "페르소나 삭제")
  public PersonaResponse.IdOut delete(@PathVariable Long personaId) {
    return personaService.delete(personaId);
  }

  @PutMapping("/persona/{personaId}/user")
  @ApiOperation(value = "활동 페르소나 설정")
  public PersonaResponse.IdOut setCurrentPersona(@PathVariable Long personaId) {
    return personaService.setCurrentPersona(personaId);
  }

  @GetMapping("/persona/user")
  @ApiOperation(value = "활동 페르소나 조회")
  public PersonaResponse.Out getCurrentPersona() {
    return personaService.getCurrentPersona();
  }

  @GetMapping("/persona")
  @ApiOperation(value = "유저의 전체 페르소나 조회")
  public List<PersonaResponse.Out> findAll() {
    return personaService.findAll();
  }

  @GetMapping("/persona/duplicate")
  @ApiOperation(value = "페르소나 닉네임 중복확인")
  public PersonaResponse.DuplicateOut duplicate(@RequestParam(value = "nickname") String nickname) {
    return personaService.duplicate(nickname);
  }


  @PostMapping("/persona/{personaId}/image")
  @ApiOperation(value = "페르소나 프로필이미지 수정")
  public PersonaResponse.IdOut saveImg(@PathVariable Long personaId,
      @RequestParam(value = "profileImg", required = true) MultipartFile profileImg)
      throws IOException {

    return personaService.saveImg(personaId, profileImg);
  }

  @GetMapping("/persona-category/sense")
  @ApiOperation(value = "페르소나 카테고리 - 발달감각 조회")
  public List<SenseResponse.Out> findSense() {
    return personaService.findSense();
  }

  @GetMapping("/persona-category/interest")
  @ApiOperation(value = "페르소나 카테고리 - 분야태그 조회")
  public List<InterestResponse.Out> findInterest() {
    return personaService.findInterest();
  }


}
