package com.eureka.ationserver.controller;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"Lounge"})
@RequiredArgsConstructor
public class LoungeController {

  private final LoungeService loungeService;

  @PostMapping("/lounge")
  @ApiOperation("라운지 생성")
  public ResponseEntity save(@RequestBody LoungeRequest loungeRequest) {
    return new ResponseEntity(loungeService.save(loungeRequest), null, HttpStatus.CREATED);
  }

  @GetMapping("/lounge")
  @ApiOperation("라운지 목록 조회")
  public ResponseEntity getAll() {
    return new ResponseEntity(loungeService.findAll(), null, HttpStatus.OK);
  }

  @GetMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 조회")
  public ResponseEntity get(@PathVariable Long loungeId) {
    return new ResponseEntity(loungeService.find(loungeId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 정보 수정")
  public ResponseEntity update(@PathVariable Long loungeId,
      @RequestBody LoungeRequest loungeRequest) {
    return new ResponseEntity(loungeService.update(loungeId, loungeRequest), null, HttpStatus.OK);

  }

  @PutMapping("/lounge/notice/{loungeId}")
  @ApiOperation("라운지 공지 수정")
  public ResponseEntity updateNotice(@PathVariable Long loungeId, @RequestBody String notice) {
    return new ResponseEntity(loungeService.updateNotice(loungeId, notice), null, HttpStatus.OK);

  }

  @GetMapping("/lounge/image")
  @ApiOperation("라운지 이미지 목록 조회")
  public ResponseEntity getImage() throws IOException {
    return new ResponseEntity(loungeService.getImage(), null, HttpStatus.OK);

  }

  @DeleteMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 삭제")
  public ResponseEntity delete(@PathVariable Long loungeId) {
    return new ResponseEntity(loungeService.delete(loungeId), null, HttpStatus.OK);

  }

  @PutMapping("/lounge/{loungeId}/end")
  @ApiOperation("라운지 종료")
  public ResponseEntity end(@PathVariable Long loungeId) {
    return new ResponseEntity(loungeService.end(loungeId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}/start")
  @ApiOperation("라운지 시작")
  public ResponseEntity start(@PathVariable Long loungeId) {
    return new ResponseEntity(loungeService.start(loungeId), null, HttpStatus.OK);

  }

  @PutMapping("/lounge/{loungeId}/enter/{personaId}")
  @ApiOperation("라운지 입장")
  public ResponseEntity enter(@PathVariable Long loungeId, @PathVariable Long personaId) {
    return new ResponseEntity(loungeService.enter(loungeId, personaId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}/exit/{personaId}")
  @ApiOperation("라운지 퇴장")
  public ResponseEntity exit(@PathVariable Long loungeId, @PathVariable Long personaId) {
    return new ResponseEntity(loungeService.exit(loungeId, personaId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}/ready/{personaId}")
  @ApiOperation("라운지 레디")
  public ResponseEntity ready(@PathVariable Long loungeId, @PathVariable Long personaId) {
    return new ResponseEntity(loungeService.ready(loungeId, personaId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}/unready/{personaId}")
  @ApiOperation("라운지 레디 해제")
  public ResponseEntity unready(@PathVariable Long loungeId, @PathVariable Long personaId) {
    return new ResponseEntity(loungeService.unready(loungeId, personaId), null, HttpStatus.OK);
  }

  @GetMapping("/lounge/{loungeId}/chat")
  @ApiOperation("라운지 채팅 조회")
  public ResponseEntity getChat(@PathVariable Long loungeId) {
    return new ResponseEntity(loungeService.getChat(loungeId), null, HttpStatus.OK);
  }

  @GetMapping("/lounge/wait")
  @ApiOperation("유저 대기 라운지 조회")
  public ResponseEntity getWait() {
    return new ResponseEntity(loungeService.getWait(), null, HttpStatus.OK);
  }

  @GetMapping("/lounge/current")
  @ApiOperation("유저 실시간 라운지 조회")
  public ResponseEntity getCurrent() {
    return new ResponseEntity(loungeService.getCurrent(), null, HttpStatus.OK);
  }

  @GetMapping("/lounge/history")
  @ApiOperation("유저 라운지 참여 이력 조회")
  public ResponseEntity getHistory() {
    return new ResponseEntity(loungeService.getHistory(), null, HttpStatus.OK);
  }

  @DeleteMapping("/lounge/history/{loungeId}")
  @ApiOperation("유저 라운지 참여 이력 삭제")
  public ResponseEntity deleteHistory(@PathVariable Long loungeId) {
    return new ResponseEntity(loungeService.deleteHistory(loungeId), null, HttpStatus.OK);
  }

  @PostMapping("/lounge/pin/{loungeId}")
  @ApiOperation("라운지 핀")
  public ResponseEntity pin(@PathVariable Long loungeId) {
    return new ResponseEntity(loungeService.pin(loungeId), null, HttpStatus.CREATED);
  }

  @DeleteMapping("/lounge/pin/{loungeId}")
  @ApiOperation("라운지 핀 삭제")
  public ResponseEntity deletePin(@PathVariable Long loungeId) {
    return new ResponseEntity(loungeService.deletePin(loungeId), null, HttpStatus.OK);
  }

  @GetMapping("/lounge/pin")
  @ApiOperation("유저 라운지 핀 목록 조회")
  public ResponseEntity getPin() {
    return new ResponseEntity(loungeService.getPin(), null, HttpStatus.OK);
  }


}
