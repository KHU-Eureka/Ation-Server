package com.eureka.ationserver.security;

import com.eureka.ationserver.advice.exception.ResourceNotFoundException;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.user.UserRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String identifyId) throws UsernameNotFoundException {
        User user = userRepository.findByIdentifyId(identifyId)
            .orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with id : " + identifyId));

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserPrincipal.create(user);
    }

}
