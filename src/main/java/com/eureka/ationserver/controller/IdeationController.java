package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.ideation.IdeationRequest;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.user.UserRepository;
import com.eureka.ationserver.service.IdeationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
@Api(tags = {"Ideation"})
@RequiredArgsConstructor
public class IdeationController {

  private final IdeationService ideationService;
  private final UserRepository userRepository;

  @PostMapping("/ideation")
  @ApiOperation(value = "아이데이션 생성")
  public ResponseEntity save(@RequestBody IdeationRequest ideationRequest) {
    return new ResponseEntity(ideationService.save(ideationRequest), null,
        HttpStatus.CREATED);
  }

  @GetMapping("/ideation")
  @ApiOperation("아이데이션 전체 조회")
  public ResponseEntity findAll(@RequestParam(value="personaId", required = true) Long personaId) {
    return new ResponseEntity(ideationService.findAll(personaId), null, HttpStatus.OK);
  }

  @GetMapping("/ideation/{ideationId}")
  @ApiOperation("아이데이션 조회")
  public ResponseEntity find(Long ideationId) {
    return new ResponseEntity(ideationService.find(ideationId), null, HttpStatus.OK);
  }

  @PostMapping("/ideation/image/{ideationId}")
  @ApiOperation(value = "아이데이션 이미지 설정")
  public ResponseEntity saveImg( @PathVariable Long ideationId, @RequestParam(value = "ideationImg") MultipartFile ideationImg) throws IOException {
    return new ResponseEntity(ideationService.saveImg(ideationId, ideationImg), null, HttpStatus.OK);
  }

  @PutMapping("/ideation/{ideationId}")
  @ApiOperation(value = "아이데이션 수정")
  public ResponseEntity update(@PathVariable Long ideationId, @RequestBody IdeationRequest ideationRequest){
    return new ResponseEntity(ideationService.update(ideationId, ideationRequest), null, HttpStatus.OK);
  }


  @DeleteMapping("/ideation/{ideationId}")
  @ApiOperation(value = "아이데이션 삭제")
  public ResponseEntity delete(@PathVariable Long ideationId){
    return new ResponseEntity(ideationService.delete(ideationId), null, HttpStatus.OK);
  }

}