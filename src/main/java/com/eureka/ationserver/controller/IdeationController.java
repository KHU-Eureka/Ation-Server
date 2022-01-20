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
  public ResponseEntity save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody
      IdeationRequest ideationRequest) {
    User user = userRepository.findByEmail(userDetails.getUsername()).get();
    return new ResponseEntity(ideationService.save(user, ideationRequest), null,
        HttpStatus.CREATED);
  }

  @PostMapping("/ideation/image/{ideationId}")
  @ApiOperation(value = "아이데이션 이미지 설정")
  public ResponseEntity saveImg(
      @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long ideationId,
      @RequestParam(value = "ideationImg", required = true) MultipartFile ideationImg) throws IOException {
    User user = userRepository.findByEmail(userDetails.getUsername()).get();
    return new ResponseEntity(ideationService.saveImg(user, ideationId, ideationImg), null, HttpStatus.OK);
  }

  @PutMapping("/ideation/{ideationId}")
  @ApiOperation(value = "아이데이션 수정")
  public ResponseEntity update(@AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long ideationId,
      @RequestBody IdeationRequest ideationRequest){
    User user = userRepository.findByEmail(userDetails.getUsername()).get();
    return new ResponseEntity(ideationService.update(user, ideationId, ideationRequest), null, HttpStatus.OK);
  }


  @DeleteMapping("/ideation/{ideationId}")
  @ApiOperation(value = "아이데이션 삭제")
  public ResponseEntity delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long ideationId){
    User user = userRepository.findByEmail(userDetails.getUsername()).get();
    return new ResponseEntity(ideationService.delete(user, ideationId), null, HttpStatus.OK);
  }

}
