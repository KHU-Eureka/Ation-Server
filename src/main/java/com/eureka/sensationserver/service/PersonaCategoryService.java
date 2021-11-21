package com.eureka.sensationserver.service;

import com.eureka.sensationserver.domain.persona.Job;
import com.eureka.sensationserver.dto.persona.InterestResponse;
import com.eureka.sensationserver.dto.persona.JobResponse;
import com.eureka.sensationserver.dto.persona.SenseResponse;
import com.eureka.sensationserver.repository.persona.InterestRepository;
import com.eureka.sensationserver.repository.persona.JobRepository;
import com.eureka.sensationserver.repository.persona.SenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonaCategoryService {
    private final SenseRepository senseRepository;
    private final JobRepository jobRepository;
    private final InterestRepository interestRepository;

    public List<SenseResponse> findSense(){
        List<SenseResponse> senseResponseList = senseRepository.findAll().stream().map(SenseResponse::new).collect(Collectors.toList());
        return senseResponseList;
    }

    public List<JobResponse> findJob(){
        List<JobResponse> jobResponseList = jobRepository.findAll().stream().map(JobResponse::new).collect(Collectors.toList());
        return jobResponseList;
    }


    public List<InterestResponse> findInterest(){
        List<InterestResponse> interestResponses = interestRepository.findAll().stream().map(InterestResponse::new).collect(Collectors.toList());
        return interestResponses;
    }
}
