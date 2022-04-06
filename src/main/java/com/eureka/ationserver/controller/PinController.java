package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.pin.PinRequest;
import com.eureka.ationserver.dto.pin.PinResponse;
import com.eureka.ationserver.service.PinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import java.util.Set;
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
@Api(tags = {"Pin"})
@RequiredArgsConstructor
public class PinController {

  private final PinService pinService;

  @PostMapping("/pin")
  @ApiOperation(value = "핀 생성")
  public PinResponse.IdOut saveNewPin(@RequestBody PinRequest.CreateInsightIn in)
      throws Exception {
    return pinService.saveNewPin(in);
  }

  @PostMapping("/pin/up")
  @ApiOperation(value = "인사이트 핀업")
  public PinResponse.IdOut pinUp(@RequestBody PinRequest.FromInsightIn in) {
    return pinService.pinUp(in);
  }


  @PutMapping("/pin/{pinId}")
  @ApiOperation(value = "핀 수정")
  public PinResponse.IdOut update(@RequestBody PinRequest.UpdateIn in,
      @PathVariable Long pinId) {
    return pinService.update(in, pinId);
  }

  @DeleteMapping("/pin/{pinId}")
  @ApiOperation(value = "핀 삭제")
  public PinResponse.IdOut delete(@PathVariable Long pinId) {
    return pinService.delete(pinId);
  }

  @PostMapping("/pin/{pinId}/image")
  @ApiOperation(value = "핀 썸네일 이미지 변경")
  public PinResponse.Out saveImg(@PathVariable Long pinId,
      @RequestParam(value = "pinImg", required = true) MultipartFile pinImg) throws IOException {
    return pinService.saveImg(pinId, pinImg);
  }

  @GetMapping("/pin")
  @ApiOperation(value = "페르소나별 핀 전체 조회")
  public List<PinResponse.Out> findAll(@RequestParam Long personaId) {
    return pinService.findAll(personaId);
  }

  @GetMapping("/pin/{pinId}")
  @ApiOperation(value = "핀 조회")
  public PinResponse.Out find(@PathVariable Long pinId) {
    return pinService.find(pinId);
  }

  @GetMapping("/pin/search")
  @ApiOperation(value = "핀 검색")
  public Set<PinResponse.Out> search(@RequestParam Long personaId, @RequestParam String keyword) {
    return pinService.search(personaId, keyword);
  }

  @GetMapping("/pin/pin-board/{pinBoardId}")
  @ApiOperation(value = "핀보드별 핀 조회")
  public List<PinResponse.Out> findByPinBoard(@PathVariable Long pinBoardId) {
    return pinService.findByPinBoard(pinBoardId);
  }
}
