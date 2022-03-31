package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.ideation.IdeationRequest;
import com.eureka.ationserver.dto.ideation.IdeationResponse;
import com.eureka.ationserver.service.IdeationService;
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
@Api(tags = {"Ideation"})
@RequiredArgsConstructor
public class IdeationController {

  private final IdeationService ideationService;

  @PostMapping("/ideation")
  @ApiOperation(value = "아이데이션 생성")
  public IdeationResponse.IdOut save(@RequestBody IdeationRequest.In in) {
    return ideationService.save(in);
  }

  @GetMapping("/ideation")
  @ApiOperation("아이데이션 전체 조회")
  public List<IdeationResponse.Out> findAll(
      @RequestParam(value = "personaId", required = true) Long personaId) {
    return ideationService.findAll(personaId);
  }

  @GetMapping("/ideation/{ideationId}")
  @ApiOperation("아이데이션 조회")
  public IdeationResponse.Out find(@PathVariable Long ideationId) {
    return ideationService.find(ideationId);
  }

  @PostMapping("/ideation/{ideationId}/image")
  @ApiOperation(value = "아이데이션 이미지 설정")
  public IdeationResponse.IdOut saveImg(@PathVariable Long ideationId,
      @RequestParam(value = "ideationImg") MultipartFile ideationImg) throws IOException {
    return ideationService.saveImg(ideationId, ideationImg);
  }

  @PutMapping("/ideation/{ideationId}/title")
  @ApiOperation(value = "아이데이션 제목 수정")
  public IdeationResponse.IdOut update(@PathVariable Long ideationId,
      @RequestBody IdeationRequest.TitleIn in) {
    return ideationService.updateTitle(ideationId, in);
  }

  @PutMapping("/ideation/{ideationId}/whiteboard")
  @ApiOperation(value = "아이데이션 화이트보드 수정")
  public IdeationResponse.IdOut updateWhiteboard(@PathVariable Long ideationId,
      @RequestBody IdeationRequest.WhiteboardIn in) {
    return ideationService.updateWhiteboard(ideationId, in);
  }

  @DeleteMapping("/ideation/{ideationId}")
  @ApiOperation(value = "아이데이션 삭제")
  public IdeationResponse.IdOut delete(@PathVariable Long ideationId) {
    return ideationService.delete(ideationId);
  }

}
