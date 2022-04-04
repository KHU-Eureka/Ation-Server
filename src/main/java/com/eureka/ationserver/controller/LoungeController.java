package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.lounge.LoungeRequest;
import com.eureka.ationserver.dto.lounge.LoungeResponse;
import com.eureka.ationserver.service.LoungeService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"Lounge"})
@RequiredArgsConstructor
public class LoungeController {

  private final LoungeService loungeService;

  @PostMapping("/lounge")
  @ApiOperation("라운지 생성")
  public LoungeResponse.IdOut save(@RequestBody LoungeRequest.In in) {
    return loungeService.save(in);
  }

  @GetMapping("/lounge")
  @ApiOperation("라운지 목록 조회")
  public List<LoungeResponse.Out> getAll() {
    return loungeService.findAll();
  }

  @GetMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 조회")
  public LoungeResponse.Out get(@PathVariable Long loungeId) {
    return loungeService.find(loungeId);
  }

  @PutMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 정보 수정")
  public LoungeResponse.IdOut update(@PathVariable Long loungeId,
      @RequestBody LoungeRequest.In in) {
    return loungeService.update(loungeId, in);

  }

  @PutMapping("/lounge/{loungeId}/notice")
  @ApiOperation("라운지 공지 수정")
  public LoungeResponse.IdOut updateNotice(@PathVariable Long loungeId,
      @RequestBody LoungeRequest.NoticeIn in) {
    return loungeService.updateNotice(loungeId, in);

  }

  @GetMapping("/lounge/image")
  @ApiOperation("라운지 이미지 목록 조회")
  public List<LoungeResponse.ImageOut> getImage() throws IOException {
    return loungeService.getImage();

  }

  @DeleteMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 삭제")
  public LoungeResponse.IdOut delete(@PathVariable Long loungeId) {
    return loungeService.delete(loungeId);

  }

  @PutMapping("/lounge/{loungeId}/end")
  @ApiOperation("라운지 종료")
  public LoungeResponse.IdOut end(@PathVariable Long loungeId) {
    return loungeService.end(loungeId);
  }

  @PutMapping("/lounge/{loungeId}/start")
  @ApiOperation("라운지 시작")
  public LoungeResponse.IdOut start(@PathVariable Long loungeId) {
    return loungeService.start(loungeId);

  }

  @PutMapping("/lounge/{loungeId}/enter/{personaId}")
  @ApiOperation("라운지 입장")
  public LoungeResponse.IdOut enter(@PathVariable Long loungeId, @PathVariable Long personaId) {
    return loungeService.enter(loungeId, personaId);
  }

  @PutMapping("/lounge/{loungeId}/exit/{personaId}")
  @ApiOperation("라운지 퇴장")
  public LoungeResponse.IdOut exit(@PathVariable Long loungeId, @PathVariable Long personaId) {
    return loungeService.exit(loungeId, personaId);
  }

  @PutMapping("/lounge/{loungeId}/ready/{personaId}")
  @ApiOperation("라운지 레디")
  public LoungeResponse.IdOut ready(@PathVariable Long loungeId, @PathVariable Long personaId) {
    return loungeService.ready(loungeId, personaId);
  }

  @PutMapping("/lounge/{loungeId}/unready/{personaId}")
  @ApiOperation("라운지 레디 해제")
  public LoungeResponse.IdOut unready(@PathVariable Long loungeId, @PathVariable Long personaId) {
    return loungeService.unready(loungeId, personaId);
  }

  @GetMapping("/lounge/{loungeId}/chat")
  @ApiOperation("라운지 채팅 조회")
  public List<LoungeResponse.ChatOut> getChat(@PathVariable Long loungeId) {
    return loungeService.getChat(loungeId);
  }

  @GetMapping("/lounge/wait")
  @ApiOperation("유저 대기 라운지 조회")
  public List<LoungeResponse.MemberStatusOut> getWait() {
    return loungeService.getWait();
  }

  @GetMapping("/lounge/current")
  @ApiOperation("유저 실시간 라운지 조회")
  public List<LoungeResponse.MemberStatusOut> getCurrent() {
    return loungeService.getCurrent();
  }

  @GetMapping("/lounge/history")
  @ApiOperation("유저 라운지 참여 이력 조회")
  public List<LoungeResponse.MemberStatusOut> getHistory() {
    return loungeService.getHistory();
  }

  @DeleteMapping("/lounge/{loungeId}/history")
  @ApiOperation("유저 라운지 참여 이력 삭제")
  public LoungeResponse.IdOut deleteHistory(@PathVariable Long loungeId) {
    return loungeService.deleteHistory(loungeId);
  }

  @PostMapping("/lounge/{loungeId}/pin")
  @ApiOperation("라운지 핀")
  public LoungeResponse.IdOut pin(@PathVariable Long loungeId) {
    return loungeService.pin(loungeId);
  }

  @DeleteMapping("/lounge/{loungeId}/pin")
  @ApiOperation("라운지 핀 삭제")
  public LoungeResponse.IdOut deletePin(@PathVariable Long loungeId) {
    return loungeService.deletePin(loungeId);
  }

  @GetMapping("/lounge/pin")
  @ApiOperation("유저 라운지 핀 목록 조회")
  public List<LoungeResponse.PinOut> getPin() {
    return loungeService.getPin();
  }


}
