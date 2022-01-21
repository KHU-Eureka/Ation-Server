package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.DuplicateException;
import com.eureka.ationserver.advice.exception.UnAuthorizedException;
import com.eureka.ationserver.config.security.details.UserDetailsImpl;
import com.eureka.ationserver.config.security.jwt.JwtUtils;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.dto.auth.JwtResponse;
import com.eureka.ationserver.dto.auth.LoginRequest;
import com.eureka.ationserver.dto.auth.SignupRequest;
import com.eureka.ationserver.dto.user.UserResponse;
import com.eureka.ationserver.repository.user.UserRepository;
import com.eureka.ationserver.util.image.ImageUtil;
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
                .name(signupRequest.getName())
                .mypageImgPath(ImageUtil.getDefaultImagePath(User.MYPAGE_PREFIX))
                .build();

        User saved = userRepository.save(user);
    }

    @Transactional
    public JwtResponse login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new JwtResponse(jwt, userDetails.getId(),userDetails.getUsername(), user.getName());

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
