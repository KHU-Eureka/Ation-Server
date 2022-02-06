package com.eureka.ationserver.service;

import com.eureka.ationserver.dto.persona.InterestResponse;
import com.eureka.ationserver.dto.sense.SenseResponse;
import com.eureka.ationserver.repository.persona.InterestRepository;
import com.eureka.ationserver.repository.persona.SenseRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonaCategoryService {
    private final SenseRepository senseRepository;
    private final InterestRepository interestRepository;

    public List<SenseResponse> findSense(){
        List<SenseResponse> senseResponseList = senseRepository.findAll().stream().map(SenseResponse::new).collect(Collectors.toList());
        return senseResponseList;
    }


    public List<InterestResponse> findInterest(){
        List<InterestResponse> interestResponses = interestRepository.findAll().stream().map(InterestResponse::new).collect(Collectors.toList());
        return interestResponses;
    }
}
