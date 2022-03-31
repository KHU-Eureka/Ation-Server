package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.pinBoard.PinBoardRequest;
import com.eureka.ationserver.dto.pinBoard.PinBoardResponse;
import com.eureka.ationserver.service.PinBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public PinBoardResponse.IdOut save(@RequestBody PinBoardRequest.In in) {
        return pinBoardService.save(in);

    }

    @PostMapping("/pin-board/image/{pinBoardId}")
    @ApiOperation(value = "핀보드 이미지 설정")
    public PinBoardResponse.Out saveImg(@PathVariable Long pinBoardId,
        @RequestParam(value = "pinBoardImg", required = true) MultipartFile pinBoardImg)
        throws IOException {
        return pinBoardService.saveImg(pinBoardId, pinBoardImg);

    }

    @GetMapping("/pin-board")
    @ApiOperation(value = "유저의 페르소나별 핀보드 조회")
    public List<PinBoardResponse.Out> findAll(@RequestParam(value = "personaId") Long personaId) {
        return pinBoardService.findAll(personaId);

    }

    @PutMapping("/pin-board/{pinBoardId}")
    @ApiOperation(value = "핀보드 수정")
    public PinBoardResponse.IdOut update(@PathVariable Long pinBoardId,
        @RequestBody PinBoardRequest.UpdateIn in) {
        return pinBoardService.update(pinBoardId, in);
    }

    @DeleteMapping("/pin-board/{pinBoardId}")
    @ApiOperation(value = "핀보드 삭제")
    public PinBoardResponse.IdOut delete(@PathVariable Long pinBoardId) {
        return pinBoardService.delete(pinBoardId);
    }
}
