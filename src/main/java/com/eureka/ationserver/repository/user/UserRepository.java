package com.eureka.ationserver.repository.user;

import com.eureka.ationserver.model.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByIdentifyId(String identifyId);

    Optional<User> findByEmail(String identifyId);

}
