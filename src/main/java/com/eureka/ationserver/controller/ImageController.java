package com.eureka.ationserver.controller;


import com.eureka.ationserver.dto.common.SimpleValueResponse;
import com.eureka.ationserver.utils.image.ImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Api(tags = {"Image"})
@RequiredArgsConstructor
public class ImageController {


  @GetMapping(
      value = "/image",
      produces = MediaType.IMAGE_PNG_VALUE
  )
  @ApiOperation("이미지 조회")
  public ResponseEntity showImage(@RequestParam String path) throws IOException {
    InputStream in = new FileInputStream(path);
    byte[] imageByteArray = IOUtils.toByteArray(in);
    in.close();
    return new ResponseEntity(imageByteArray, null, HttpStatus.OK);
  }

  @PostMapping("/image")
  @ApiOperation("이미지 저장")
  public ResponseEntity saveImage(@RequestParam(value="image", required = true)MultipartFile image) throws IOException{
    List<String> pathList = ImageUtil.getImagePath("img", System.currentTimeMillis());
    File file = new File(pathList.get(1));
    image.transferTo(file);
    return new ResponseEntity(new SimpleValueResponse<String>(pathList.get(0)), null, HttpStatus.CREATED);
  }
}
