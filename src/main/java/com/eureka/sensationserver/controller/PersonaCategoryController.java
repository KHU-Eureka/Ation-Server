package com.eureka.sensationserver.controller;

import com.eureka.sensationserver.service.PersonaCategoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"Persona Category"})
@RequiredArgsConstructor
public class PersonaCategoryController {

    private final PersonaCategoryService personaCategoryService;

    @GetMapping("/persona-category/sense")
    public ResponseEntity findSense(){
        return new ResponseEntity(personaCategoryService.findSense(),null, HttpStatus.OK);
    }

    @GetMapping("/persona-category/job")
    public ResponseEntity findJob(){
        return new ResponseEntity(personaCategoryService.findJob(),null, HttpStatus.OK);
    }

    @GetMapping("/persona-category/interest")
    public ResponseEntity findInterest(){
        return new ResponseEntity(personaCategoryService.findInterest(),null, HttpStatus.OK);
    }
}
