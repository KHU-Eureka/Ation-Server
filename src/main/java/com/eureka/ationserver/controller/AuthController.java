package com.eureka.ationserver.controller;

import com.eureka.ationserver.service.AuthService;
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
@Api(tags = {"Auth"})
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/user")
    @ApiOperation(value = "로그인 유저 조회")
    public ResponseEntity loggedIn() {
        return new ResponseEntity(authService.getLoggedInUser(), null, HttpStatus.OK);
    }

}
