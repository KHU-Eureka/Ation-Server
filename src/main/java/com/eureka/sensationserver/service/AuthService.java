package com.eureka.sensationserver.service;

import com.eureka.sensationserver.domain.User;
import com.eureka.sensationserver.dto.auth.SignupRequest;
import com.eureka.sensationserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public void signUp(SignupRequest signupRequest){
        User user = signupRequest.toEntity();
        userRepository.save(user);
    }
}
