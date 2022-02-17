package com.eureka.ationserver.controller;

import com.eureka.ationserver.service.PersonaCategoryService;
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
@Api(tags = {"PersonaCategory"})
@RequiredArgsConstructor
public class PersonaCategoryController {

    private final PersonaCategoryService personaCategoryService;

    @GetMapping("/persona-category/sense")
    @ApiOperation(value="페르소나 카테고리 - 발달감각 조회")
    public ResponseEntity findSense(){
        return new ResponseEntity(personaCategoryService.findSense(),null, HttpStatus.OK);
    }

    @GetMapping("/persona-category/interest")
    @ApiOperation(value="페르소나 카테고리 - 분야태그 조회")
    public ResponseEntity findInterest(){
        return new ResponseEntity(personaCategoryService.findInterest(),null, HttpStatus.OK);
    }
}
