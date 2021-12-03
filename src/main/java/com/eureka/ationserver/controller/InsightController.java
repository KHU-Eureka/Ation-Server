package com.eureka.ationserver.controller;


import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.service.InsightService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@Api(tags = {"Insight"})
@RequiredArgsConstructor
public class InsightController {


    private final InsightService insightService;

    @PostMapping("/insight")
    public ResponseEntity save(@RequestBody InsightRequest insightRequest) throws IOException {
        return new ResponseEntity(insightService.save(insightRequest),null, HttpStatus.CREATED);
    }

    @GetMapping("/insight")
    public ResponseEntity findAll(){
        return new ResponseEntity(insightService.findAll(),null, HttpStatus.CREATED);
    }

    @GetMapping("/insight/main-category")
    public ResponseEntity findAllMainCategory(){
        return new ResponseEntity(insightService.findAllMainCategory(), null, HttpStatus.OK);
    }

    @GetMapping("/insight/sub-category")
    public ResponseEntity findSubCategory(@RequestParam(value="insightMainCategoryId")Long insightMainCategoryId){
        return new ResponseEntity(insightService.findSubCategory(insightMainCategoryId), null, HttpStatus.OK);
    }



}
