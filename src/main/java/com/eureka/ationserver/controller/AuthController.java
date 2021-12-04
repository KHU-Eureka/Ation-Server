package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.common.MessageResponse;
import com.eureka.ationserver.dto.auth.LoginRequest;
import com.eureka.ationserver.dto.auth.SignupRequest;
import com.eureka.ationserver.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Api(tags = {"Authentication"})
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/auth/signup")
    @ApiOperation(value="회원가입")
    public ResponseEntity signUp(@Valid @RequestBody SignupRequest signupRequest){
        authService.register(signupRequest);
        return new ResponseEntity(new MessageResponse("SignUp Success"), null, HttpStatus.CREATED);
    }


    @PostMapping("/auth/login")
    @ApiOperation(value="로그인")
    public ResponseEntity logIn(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity(authService.login(loginRequest), null, HttpStatus.OK);
    }

    @GetMapping("/auth")
    @ApiOperation(value="로그인된 유저 조회")
    public ResponseEntity<?> loggedIn(){
        return new ResponseEntity(authService.checkAuthState(), null, HttpStatus.OK);
    }





}
