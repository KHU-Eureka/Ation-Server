package com.eureka.ationserver.controller;


import com.eureka.ationserver.dto.lounge.LoungeRequest;
import com.eureka.ationserver.service.LoungeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"Lounge"})
@RequiredArgsConstructor
public class LoungeController {

  private final LoungeService loungeService;

  @PostMapping("/lounge")
  @ApiOperation("라운지 생성")
  public ResponseEntity save(@RequestBody LoungeRequest loungeRequest) throws Exception {
    return new ResponseEntity(loungeService.save(loungeRequest), null, HttpStatus.CREATED);

  }

  @GetMapping("/lounge")
  @ApiOperation("라운지 목록 조회")
  public ResponseEntity getAll() throws Exception {
    return ResponseEntity.ok(loungeService.findAll());
  }

  @GetMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 조회")
  public ResponseEntity get(@PathVariable Long loungeId) throws Exception {
    return ResponseEntity.ok(loungeService.find(loungeId));
  }

  @PutMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 정보 수정")
  public ResponseEntity update(@RequestBody LoungeRequest loungeRequest) throws Exception {
    return new ResponseEntity(loungeService.save(loungeRequest), null, HttpStatus.CREATED);

  }

  @PutMapping("/lounge/{loungeId}/notice")
  @ApiOperation("라운지 공지 수정")
  public ResponseEntity updateNotice(@RequestBody LoungeRequest loungeRequest) throws Exception {
    return new ResponseEntity(loungeService.save(loungeRequest), null, HttpStatus.CREATED);

  }

  @DeleteMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 삭제")
  public ResponseEntity delete(@RequestBody LoungeRequest loungeRequest) throws Exception {
    return new ResponseEntity(loungeService.save(loungeRequest), null, HttpStatus.CREATED);

  }

  @DeleteMapping("/lounge/{loungeId}/close")
  @ApiOperation("라운지 종료")
  public ResponseEntity close(@RequestBody LoungeRequest loungeRequest) throws Exception {
    return new ResponseEntity(loungeService.save(loungeRequest), null, HttpStatus.CREATED);

  }

  @DeleteMapping("/lounge/{loungeId}/open")
  @ApiOperation("라운지 시작")
  public ResponseEntity start(@RequestBody LoungeRequest loungeRequest) throws Exception {
    return new ResponseEntity(loungeService.save(loungeRequest), null, HttpStatus.CREATED);

  }

  @DeleteMapping("/lounge/{loungeId}/chat")
  @ApiOperation("라운지 채팅 조회")
  public ResponseEntity getChat(@RequestBody LoungeRequest loungeRequest) throws Exception {
    return new ResponseEntity(loungeService.save(loungeRequest), null, HttpStatus.CREATED);

  }

}
