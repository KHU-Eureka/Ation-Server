package com.eureka.ationserver.controller;

import com.eureka.ationserver.service.RecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"Recommend"})
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/recommend/view-matrix")
    @ApiOperation(value="유저별 인사이트 조회 결과 matrix 생성")
    public ResponseEntity createViewMatrix(){
        return new ResponseEntity(recommendService.createUserInsightViewMatrix(),null, HttpStatus.OK);
    }

    @GetMapping("/recommend/insight-matrix")
    @ApiOperation(value="인사이트 정보 matrix 생성")
    public ResponseEntity createInsightMatrix(){
        return new ResponseEntity(recommendService.createInsightMatrix(),null, HttpStatus.OK);
    }

    @GetMapping("/recommend/user-matrix")
    @ApiOperation(value="유저 정보 matrix 생성")
    public ResponseEntity createUserMatrix(){
        return new ResponseEntity(recommendService.createUserMatrix(),null, HttpStatus.OK);
    }
}
