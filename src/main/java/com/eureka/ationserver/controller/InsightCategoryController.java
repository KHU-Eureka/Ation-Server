package com.eureka.ationserver.controller;

import com.eureka.ationserver.service.InsightCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"Insight Category"})
@RequiredArgsConstructor
public class InsightCategoryController {

    private final InsightCategoryService insightCategoryService;

    @GetMapping("/insight-category/main")
    @ApiOperation(value="인사이트 대분류 조회")
    public ResponseEntity findAllMainCategory(){
        return new ResponseEntity(insightCategoryService.findAllMainCategory(), null, HttpStatus.OK);
    }

    @GetMapping("/insight-category/sub")
    @ApiOperation(value="인사이트 중분류 조회")
    public ResponseEntity findSubCategory(@RequestParam(value="insightMainCategoryId")Long insightMainCategoryId){
        return new ResponseEntity(insightCategoryService.findSubCategory(insightMainCategoryId), null, HttpStatus.OK);
    }
}
