package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.common.MessageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"Connection"})
@RequestMapping("/api")
public class TestController {
    @GetMapping("/test")
    @ApiOperation(value="커넥션 테스트")
    public ResponseEntity<?> Test(){
        return ResponseEntity.ok(new MessageResponse("Connection Success"));
    }
}
