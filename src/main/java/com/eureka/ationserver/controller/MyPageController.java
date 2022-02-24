package com.eureka.ationserver.controller;

import com.eureka.ationserver.service.MyPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Api(tags = {"MyPage"})
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @SneakyThrows
    @PostMapping("/mypage/image")
    @ApiOperation(value = "마이페이지 커버 이미지 변경")
    public ResponseEntity saveImg(
        @RequestParam(value = "myPageImg", required = true) MultipartFile myPageImg) {
        return new ResponseEntity(myPageService.saveImg(myPageImg), null, HttpStatus.OK);
    }
}
