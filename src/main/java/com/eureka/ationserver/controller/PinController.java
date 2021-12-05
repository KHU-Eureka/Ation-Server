package com.eureka.ationserver.controller;

import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.dto.pin.InsightPinRequest;
import com.eureka.ationserver.dto.pin.PinRequest;
import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.repository.UserRepository;
import com.eureka.ationserver.service.PinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@Api(tags = {"Insight Pin"})
@RequiredArgsConstructor
public class PinController {

    private final UserRepository userRepository;
    private final PinService pinService;

    @PostMapping("/pin")
    @ApiOperation(value="핀 생성")
    public ResponseEntity saveNewPin(@AuthenticationPrincipal UserDetails userDetails, @RequestBody InsightPinRequest insightPinRequest) throws IOException {
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinService.saveNewPin(user, insightPinRequest),null, HttpStatus.CREATED);
    }

    @PostMapping("/pin/up")
    @ApiOperation(value="인사이트 핀업")
    public ResponseEntity pinUp(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PinRequest pinRequest){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinService.pinUp(user, pinRequest),null, HttpStatus.CREATED);
    }
}
