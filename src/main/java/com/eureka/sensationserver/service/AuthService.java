package com.eureka.sensationserver.service;

import com.eureka.sensationserver.advice.exception.DuplicateException;
import com.eureka.sensationserver.advice.exception.UnAuthorizedException;
import com.eureka.sensationserver.config.security.details.UserDetailsImpl;
import com.eureka.sensationserver.config.security.jwt.JwtUtils;
import com.eureka.sensationserver.domain.User;
import com.eureka.sensationserver.dto.auth.JwtResponse;
import com.eureka.sensationserver.dto.auth.LoginRequest;
import com.eureka.sensationserver.dto.auth.SignupRequest;
import com.eureka.sensationserver.dto.user.UserResponse;
import com.eureka.sensationserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final JwtUtils jwtUtils;

    @Transactional
    public void register(SignupRequest signupRequest){
        userRepository.findByEmail(signupRequest.getEmail()).ifPresent(m ->{
            throw new DuplicateException();
        });
        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(encoder.encode(signupRequest.getPassword()))
                .build();

        userRepository.save(user);
    }

    @Transactional
    public JwtResponse login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtResponse(jwt, userDetails.getId(),userDetails.getUsername());

    }

    public UserResponse checkAuthState(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if(principal == "anonymousUser"){
            throw new UnAuthorizedException();
        }
        UserDetails userDetails = (UserDetails) principal;
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new UserResponse(user);

    }
}
