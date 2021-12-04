package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.service.PinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@Api(tags = {"Insight Pin"})
@RequiredArgsConstructor
public class PinController {

    private final PinService pinService;

    @PostMapping("/pin")
    @ApiOperation(value="핀 생성")
    public ResponseEntity saveNewPin(@RequestBody InsightRequest insightRequest, @RequestParam(value="pinBoardId") Long pinBoardId) throws IOException {
        return new ResponseEntity(pinService.saveNewPin(insightRequest, pinBoardId),null, HttpStatus.CREATED);
    }
}
