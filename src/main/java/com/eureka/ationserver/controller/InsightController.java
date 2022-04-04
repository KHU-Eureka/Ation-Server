package com.eureka.ationserver.controller;


import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.dto.insight.InsightResponse;
import com.eureka.ationserver.service.InsightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Api(tags = {"Insight"})
@RequiredArgsConstructor
public class InsightController {

  private final InsightService insightService;


  @SneakyThrows
  @PostMapping("/insight")
  @ApiOperation(value = "인사이트 생성")
  public InsightResponse.IdOut savePublic(@RequestBody InsightRequest.In in) {
    return insightService.savePublic(in);
  }

  @SneakyThrows
  @PostMapping("/insight/{insightId}/image")
  @ApiOperation(value = "인사이트 썸네일 이미지 변경")
  public InsightResponse.Out saveImg(@PathVariable Long insightId,
      @RequestParam(value = "insightImg", required = true) MultipartFile insightImg) {
    return insightService.saveImg(insightId, insightImg);
  }

  @GetMapping("/insight")
  @ApiOperation(value = "인사이트 전체 조회")
  public List<InsightResponse.Out> findPublicAll() {
    return insightService.findPublicAll();
  }

  @GetMapping("/insight/{insightId}")
  @ApiOperation(value = "인사이트 조회")
  public InsightResponse.Out findPublic(@PathVariable Long insightId) {
    return insightService.findPublic(insightId);
  }

  @GetMapping("/insight/{mainCategoryId}/main-category")
  @ApiOperation(value = "인사이트 카테고리별 조회")
  public List<InsightResponse.Out> findByMainCategory(@PathVariable Long mainCategoryId) {
    return insightService.findByMainCategory(mainCategoryId);
  }

  @GetMapping("/insight/search")
  @ApiOperation(value = "인사이트 검색")
  public Set<InsightResponse.Out> search(@RequestParam String keyword) {
    return insightService.search(keyword);
  }

  @GetMapping("/insight/recommend")
  @ApiOperation(value = "추천 인사이트 조회")
  public List<InsightResponse.Out> getRecommend() throws Exception {
    return insightService.getRecommend();
  }

  @GetMapping("/insight/random")
  @ApiOperation(value = "랜덤 인사이트 조회")
  public List<InsightResponse.Out> getRandom() throws Exception {
    return insightService.getRandom();
  }

}
