package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.pinBoard.PinBoardRequest;
import com.eureka.ationserver.dto.pinBoard.PinBoardUpdateRequest;
import com.eureka.ationserver.service.PinBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Api(tags = {"PinBoard"})
@RequiredArgsConstructor
public class PinBoardController {

    private final PinBoardService pinBoardService;

    @PostMapping("/pin-board")
    @ApiOperation(value = "핀보드 생성")
    public ResponseEntity save(@RequestBody PinBoardRequest pinBoardRequest) {
        return new ResponseEntity(pinBoardService.save(pinBoardRequest), null, HttpStatus.CREATED);

    }

    @PostMapping("/pin-board/image/{pinBoardId}")
    @ApiOperation(value = "핀보드 이미지 설정")
    public ResponseEntity saveImg(@PathVariable Long pinBoardId,
        @RequestParam(value = "pinBoardImg", required = true) MultipartFile pinBoardImg)
        throws IOException {
        return new ResponseEntity(pinBoardService.saveImg(pinBoardId, pinBoardImg), null,
            HttpStatus.OK);
    }

    @GetMapping("/pin-board")
    @ApiOperation(value = "유저의 페르소나별 핀보드 조회")
    public ResponseEntity findAll(@RequestParam(value = "personaId") Long personaId) {
        return new ResponseEntity(pinBoardService.findAll(personaId), null, HttpStatus.OK);

    }

    @PutMapping("/pin-board/{pinBoardId}")
    @ApiOperation(value = "핀보드 수정")
    public ResponseEntity update(@PathVariable Long pinBoardId,
        @RequestBody PinBoardUpdateRequest pinBoardRequest) {
        return new ResponseEntity(pinBoardService.update(pinBoardId, pinBoardRequest), null,
            HttpStatus.OK);
    }

    @DeleteMapping("/pin-board/{pinBoardId}")
    @ApiOperation(value = "핀보드 삭제")
    public ResponseEntity delete(@PathVariable Long pinBoardId) {
        return new ResponseEntity(pinBoardService.delete(pinBoardId), null, HttpStatus.OK);


    }
}
