package com.eureka.ationserver.service;

import com.eureka.ationserver.config.security.jwt.JwtAuthProvider;
import com.eureka.ationserver.dto.auth.JwtResponse;
import com.eureka.ationserver.dto.auth.LoginRequest;
import com.eureka.ationserver.dto.auth.SignupRequest;
import com.eureka.ationserver.dto.user.UserResponse;
import com.eureka.ationserver.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthProvider jwtAuthProvider;

    @Transactional
    public void register(SignupRequest signupRequest){

    }

    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtAuthProvider.generateJwtToken(authentication);
//
//        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
//
//        User user = userRepository.findByEmail(userDetails.getUsername()).get();
//        return new JwtResponse(jwt, userDetails.getId(),userDetails.getUsername(), user.getName());
        return null;

    }

    public UserResponse checkAuthState() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return new UserResponse(userRepository.findByIdentifyId(userDetails.getUsername()).get());
    }
}
