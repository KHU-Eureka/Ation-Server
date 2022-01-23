package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.lounge.LoungeRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"Lounge"})
public class LoungeController {

  @PostMapping("/lounge")
  @ApiOperation(value="라운지 생성")
  public ResponseEntity save(@RequestBody LoungeRequest loungeRequest){
    return null;
  }

  @PutMapping("/lounge/{loungeId}")
  @ApiOperation(value="라운지 수정")
  public ResponseEntity update(@RequestBody LoungeRequest loungeRequest){
    return null;
  }

  @DeleteMapping("/lounge/{loungeId}")
  @ApiOperation(value="라운지 삭제")
  public ResponseEntity remove(@RequestBody LoungeRequest loungeRequest){
    return null;
  }

  @GetMapping("/lounge/{loungeId}")
  @ApiOperation(value="라운지 조회")
  public ResponseEntity find(@RequestBody LoungeRequest loungeRequest){
    return null;
  }

  @GetMapping("/lounge")
  @ApiOperation(value="라운지 전체 조회")
  public ResponseEntity findAll(@RequestBody LoungeRequest loungeRequest){
    return null;
  }

  @GetMapping("/lounge/{loungeId}/members")
  @ApiOperation(value="라운지 참여멤버 조회")
  public ResponseEntity findMember(@RequestBody LoungeRequest loungeRequest){
    return null;
  }

  @PutMapping("/lounge/{loungeId}/entrance")
  @ApiOperation(value="라운지 입장")
  public ResponseEntity entrance(@RequestBody LoungeRequest loungeRequest){
    return null;
  }

  @DeleteMapping("/lounge/{loungeId}/exit")
  @ApiOperation(value="라운지 퇴장")
  public ResponseEntity exit(@RequestBody LoungeRequest loungeRequest){
    return null;
  }

  @PutMapping("/lounge/{loungeId}/end")
  @ApiOperation(value="라운지 종료")
  public ResponseEntity end(@RequestBody LoungeRequest loungeRequest){
    return null;
  }

  @PutMapping("/lounge/now")
  @ApiOperation(value="현재 참여 라운지 조회")
  public ResponseEntity current(@RequestBody LoungeRequest loungeRequest){
    return null;
  }


  @GetMapping("/lounge/pin")
  @ApiOperation(value="핀 라운지 조회")
  public ResponseEntity findPin(@RequestBody LoungeRequest loungeRequest){
    return null;
  }

  @GetMapping("/lounge/history")
  @ApiOperation(value="참여했던 라운지 조회")
  public ResponseEntity findHistory(@RequestBody LoungeRequest loungeRequest){
    return null;
  }
}
