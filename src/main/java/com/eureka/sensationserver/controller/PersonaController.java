package com.eureka.sensationserver.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"Persona"})
@RequiredArgsConstructor
public class PersonaController {

    @GetMapping("/persona")
    public String test(){
        return "ss";
    }
}
