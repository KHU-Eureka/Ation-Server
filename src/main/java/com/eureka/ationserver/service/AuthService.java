package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.UnAuthorizedException;
import com.eureka.ationserver.dto.user.UserResponse;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public UserResponse.LoggedIn getLoggedInUser() {
        User user = this.auth();
        return UserResponse.toLoggedIn(user);
    }

    public User auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal == "anonymousUser") {
            throw new UnAuthorizedException();
        }
        UserDetails userDetails = (UserDetails) principal;
        return userRepository.findByIdentifyId(userDetails.getUsername()).get();
    }
}
