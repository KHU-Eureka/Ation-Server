package com.eureka.sensationserver.dto.persona;

import com.eureka.sensationserver.domain.persona.Job;
import com.eureka.sensationserver.domain.persona.PersonaJob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {
    private Long jobId;
    private String name;

    public JobResponse(PersonaJob personaJob){
        jobId = personaJob.getJob().getId();
        name = personaJob.getJob().getName();
    }

    public JobResponse(Job job){
        jobId = job.getId();
        name = job.getName();
    }
}

