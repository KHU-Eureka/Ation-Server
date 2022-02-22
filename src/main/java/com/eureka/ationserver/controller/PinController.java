package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.pin.InsightPinRequest;
import com.eureka.ationserver.dto.pin.PinRequest;
import com.eureka.ationserver.dto.pin.PinUpdateRequest;
import com.eureka.ationserver.service.PinService;
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
@Api(tags = {"Pin"})
@RequiredArgsConstructor
public class PinController {

  private final PinService pinService;

  @PostMapping("/pin")
  @ApiOperation(value = "핀 생성")
  public ResponseEntity saveNewPin(@RequestBody InsightPinRequest insightPinRequest)
      throws Exception {
    return new ResponseEntity(pinService.saveNewPin(insightPinRequest), null,
        HttpStatus.CREATED);
  }

  @PostMapping("/pin/up")
  @ApiOperation(value = "인사이트 핀업")
  public ResponseEntity pinUp(@RequestBody PinRequest pinRequest) {
    return new ResponseEntity(pinService.pinUp(pinRequest), null, HttpStatus.CREATED);
  }


  @PutMapping("/pin/{pinId}")
  @ApiOperation(value = "핀 수정")
  public ResponseEntity update(@RequestBody PinUpdateRequest pinUpdateRequest,
      @PathVariable Long pinId) {
    return new ResponseEntity(pinService.update(pinUpdateRequest, pinId), null, HttpStatus.OK);
  }

  @DeleteMapping("/pin/{pinId}")
  @ApiOperation(value = "핀 삭제")
  public ResponseEntity delete(@PathVariable Long pinId) {
    return new ResponseEntity(pinService.delete(pinId), null, HttpStatus.OK);
  }

  @PostMapping("/pin/image/{pinId}")
  @ApiOperation(value = "핀 썸네일 이미지 변경")
  public ResponseEntity saveImg(@PathVariable Long pinId,
      @RequestParam(value = "pinImg", required = true) MultipartFile pinImg) throws IOException {
    return new ResponseEntity(pinService.saveImg(pinId, pinImg), null, HttpStatus.OK);
  }

  @GetMapping("/pin")
  @ApiOperation(value = "페르소나별 핀 전체 조회")
  public ResponseEntity findAll(@RequestParam Long personaId) {
    return new ResponseEntity(pinService.findAll(personaId), null, HttpStatus.OK);
  }

  @GetMapping("/pin/{pinId}")
  @ApiOperation(value = "핀 조회")
  public ResponseEntity find(@PathVariable Long pinId) {
    return new ResponseEntity(pinService.find(pinId), null, HttpStatus.OK);
  }

  @GetMapping("/pin/search")
  @ApiOperation(value = "핀 검색")
  public ResponseEntity search(@RequestParam Long personaId, @RequestParam String keyword) {
    return new ResponseEntity(pinService.search(personaId, keyword), null, HttpStatus.OK);
  }

  @GetMapping("/pin/pin-board/{pinBoardId}")
  @ApiOperation(value = "핀보드별 핀 조회")
  public ResponseEntity findByPinBoard(@PathVariable Long pinBoardId) {
    return new ResponseEntity(pinService.findByPinBoard(pinBoardId), null, HttpStatus.OK);
  }
}
