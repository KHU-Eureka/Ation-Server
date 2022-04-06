package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.user.UserResponse;
import com.eureka.ationserver.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Api(tags = {"User"})
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/user/logged-in")
  @ApiOperation(value = "로그인 유저 조회")
  public UserResponse.Out loggedIn() {
    return userService.getLoggedInUser();
  }

  @SneakyThrows
  @PostMapping("/user/my-page")
  @ApiOperation(value = "마이페이지 커버 이미지 변경")
  public UserResponse.IdOut saveImg(
      @RequestParam(value = "myPageImg", required = true) MultipartFile myPageImg) {
    return userService.saveImg(myPageImg);
  }

}
