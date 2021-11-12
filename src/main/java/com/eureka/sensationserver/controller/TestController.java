package com.eureka.sensationserver.controller;

import com.eureka.sensationserver.dto.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<?> Test(){
        return ResponseEntity.ok(new MessageResponse("Connection Success"));
    }
}
