package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.UnAuthorizedException;
import com.eureka.ationserver.dto.user.UserResponse;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.user.UserRepository;
import com.eureka.ationserver.utils.image.ImageUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserResponse.Out getLoggedInUser() {
    User user = this.auth();
    return UserResponse.toOut(user);
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

  @Transactional
  public UserResponse.IdOut saveImg(MultipartFile myPageImg) throws IOException {
    User user = this.auth();

    List<String> pathList = ImageUtil.getImagePath(User.MYPAGE_PREFIX, user.getId());
    File file = new File(pathList.get(1));
    myPageImg.transferTo(file);
    user.setMyPageImgPath(pathList.get(0));

    return UserResponse.toIdOut(user.getId());
  }
}
