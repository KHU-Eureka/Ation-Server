package com.eureka.ationserver.controller;


import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.service.InsightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/insight/public")
    @ApiOperation(value="인사이트 생성")
    public ResponseEntity savePublic(@RequestBody InsightRequest insightRequest) throws IOException {
        return new ResponseEntity(insightService.savePublic(insightRequest),null, HttpStatus.CREATED);
    }



    @GetMapping("/insight/public")
    public ResponseEntity findPublicAll(){
        return new ResponseEntity(insightService.findPublicAll(),null, HttpStatus.CREATED);
    }




}
