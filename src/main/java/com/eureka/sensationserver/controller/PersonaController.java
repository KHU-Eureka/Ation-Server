package com.eureka.sensationserver.controller;

import com.eureka.sensationserver.domain.User;
import com.eureka.sensationserver.dto.persona.PersonaRequest;
import com.eureka.sensationserver.repository.UserRepository;
import com.eureka.sensationserver.service.PersonaService;
import io.swagger.annotations.Api;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(tags = {"Persona"})
@RequiredArgsConstructor
public class PersonaController {
    private final PersonaService personaService;

    private final UserRepository userRepository;

    @GetMapping("/persona/{personaId}")
    public ResponseEntity currentPersona( @PathVariable Long personaId){
        if(personaId == null){
            return new ResponseEntity(personaService.findAll(), null, HttpStatus.OK);

        }else{
            return new ResponseEntity(personaService.find(personaId), null, HttpStatus.OK);

        }

    }


    @PostMapping("/persona")
    public ResponseEntity save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PersonaRequest personaRequest){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(personaService.save(user, personaRequest),null, HttpStatus.CREATED);
    }

    @PutMapping("/persona/{personaId}")
    public ResponseEntity update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long personaId,@RequestBody PersonaRequest personaRequest){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(personaService.update(user, personaId, personaRequest), null, HttpStatus.OK);

    }

    @DeleteMapping("/persona/{personaId}")
    public ResponseEntity delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long personaId){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(personaService.delete(user, personaId), null, HttpStatus.OK);

    }


}
