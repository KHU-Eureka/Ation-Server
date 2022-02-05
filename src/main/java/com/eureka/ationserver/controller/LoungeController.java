package com.eureka.ationserver.controller;


import com.eureka.ationserver.config.security.details.UserDetailsImpl;
import com.eureka.ationserver.dto.lounge.LoungeRequest;
import com.eureka.ationserver.service.LoungeService;
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
@Api(tags = {"Lounge"})
@RequiredArgsConstructor
public class LoungeController {

  private final LoungeService loungeService;

  @PostMapping("/lounge")
  @ApiOperation("라운지 생성")
  public ResponseEntity save(@RequestBody LoungeRequest loungeRequest){
    return new ResponseEntity(loungeService.save(loungeRequest), null, HttpStatus.CREATED);
  }

  @GetMapping("/lounge")
  @ApiOperation("라운지 목록 조회")
  public ResponseEntity getAll(){
    return new ResponseEntity(loungeService.findAll(), null, HttpStatus.OK);
  }

  @GetMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 조회")
  public ResponseEntity get(@PathVariable Long loungeId){
    return new ResponseEntity(loungeService.find(loungeId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 정보 수정")
  public ResponseEntity update(@PathVariable Long loungeId, @RequestBody LoungeRequest loungeRequest){
    return new ResponseEntity(loungeService.update(loungeId, loungeRequest), null, HttpStatus.OK);

  }

  @PutMapping("/lounge/{loungeId}/notice")
  @ApiOperation("라운지 공지 수정")
  public ResponseEntity updateNotice(@PathVariable Long loungeId, @RequestBody String notice){
    return new ResponseEntity(loungeService.updateNotice(loungeId, notice), null, HttpStatus.OK);

  }

  @PutMapping("/lounge/{loungeId}/image")
  @ApiOperation("라운지 이미지 변경")
  public ResponseEntity saveImg(@PathVariable Long loungeId, @RequestParam(value = "loungeImg", required = true) MultipartFile loungeImg) throws IOException {
    return new ResponseEntity(loungeService.saveImg(loungeId, loungeImg), null, HttpStatus.OK);

  }

  @DeleteMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 삭제")
  public ResponseEntity delete(@PathVariable Long loungeId){
    return new ResponseEntity(loungeService.delete(loungeId), null, HttpStatus.OK);

  }

  @PutMapping("/lounge/{loungeId}/close")
  @ApiOperation("라운지 종료")
  public ResponseEntity close(@PathVariable Long loungeId){
    return new ResponseEntity(loungeService.close(loungeId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}/start")
  @ApiOperation("라운지 시작")
  public ResponseEntity start(@PathVariable Long loungeId){
    return new ResponseEntity(loungeService.start(loungeId), null, HttpStatus.OK);

  }

  @PutMapping("/lounge/{loungeId}/enter/{personaId}")
  @ApiOperation("라운지 입장")
  public ResponseEntity enter(@PathVariable Long loungeId, @PathVariable Long personaId){
    return new ResponseEntity(loungeService.enter(loungeId, personaId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}/exit/{personaId}")
  @ApiOperation("라운지 퇴장")
  public ResponseEntity exit(@PathVariable Long loungeId, @PathVariable Long personaId){
    return new ResponseEntity(loungeService.exit(loungeId,personaId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}/ready/{personaId}")
  @ApiOperation("라운지 레디")
  public ResponseEntity ready(@PathVariable Long loungeId, @PathVariable Long personaId){
    return new ResponseEntity(loungeService.ready(loungeId,personaId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}/unready/{personaId}")
  @ApiOperation("라운지 레디 해제")
  public ResponseEntity unready(@PathVariable Long loungeId, @PathVariable Long personaId){
    return new ResponseEntity(loungeService.unready(loungeId,personaId), null, HttpStatus.OK);
  }


  @GetMapping("/lounge/{loungeId}/chat")
  @ApiOperation("라운지 채팅 조회")
  public ResponseEntity getChat(@PathVariable Long loungeId){
    return new ResponseEntity(loungeService.getChat(loungeId), null, HttpStatus.OK);
  }

  @GetMapping("/lounge/wait")
  @ApiOperation("유저 대기 라운지 조회")
  public ResponseEntity getWait(@AuthenticationPrincipal UserDetailsImpl userDetails){
    return new ResponseEntity(loungeService.getWait(userDetails), null, HttpStatus.OK);
  }

  @GetMapping("/lounge/current")
  @ApiOperation("유저 실시간 라운지 조회")
  public ResponseEntity getCurrent(@AuthenticationPrincipal UserDetailsImpl userDetails){
    return new ResponseEntity(loungeService.getCurrent(userDetails), null, HttpStatus.OK);
  }

  @GetMapping("/lounge/history")
  @ApiOperation("유저 라운지 참여 이력 조회")
  public ResponseEntity getHistory(@AuthenticationPrincipal UserDetailsImpl userDetails){
    return new ResponseEntity(loungeService.getHistory(userDetails), null, HttpStatus.OK);
  }

}
