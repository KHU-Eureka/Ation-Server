package com.eureka.sensationserver.controller;

import com.eureka.sensationserver.config.security.jwt.JwtUtils;
import com.eureka.sensationserver.dto.MessageResponse;
import com.eureka.sensationserver.dto.auth.LoginRequest;
import com.eureka.sensationserver.dto.auth.SignupRequest;
import com.eureka.sensationserver.repository.UserRepository;
import com.eureka.sensationserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest signupRequest){
        authService.register(signupRequest);
        return ResponseEntity.ok(new MessageResponse("SignUp Success"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @GetMapping("/login")
    public ResponseEntity<?> loggedIn(){
        return ResponseEntity.ok(authService.checkAuthState());
    }





}
