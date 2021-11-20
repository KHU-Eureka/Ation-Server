package com.eureka.sensationserver.controller;

import com.eureka.sensationserver.dto.common.MessageResponse;
import com.eureka.sensationserver.dto.auth.LoginRequest;
import com.eureka.sensationserver.dto.auth.SignupRequest;
import com.eureka.sensationserver.service.AuthService;
import io.swagger.annotations.Api;
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
    public ResponseEntity signUp(@Valid @RequestBody SignupRequest signupRequest){
        authService.register(signupRequest);
        return new ResponseEntity(new MessageResponse("SignUp Success"), null, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity logIn(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity(authService.authenticateUser(loginRequest), null, HttpStatus.OK);
    }

    @GetMapping("/auth/login")
    public ResponseEntity<?> loggedIn(){
        return new ResponseEntity(authService.checkAuthState(), null, HttpStatus.OK);
    }





}
