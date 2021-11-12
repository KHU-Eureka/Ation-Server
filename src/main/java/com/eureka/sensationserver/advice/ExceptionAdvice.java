package com.eureka.sensationserver.advice;

import com.eureka.sensationserver.advice.exception.DuplicateException;
import com.eureka.sensationserver.advice.exception.NotAuthenticatedException;
import com.eureka.sensationserver.dto.common.MessageResponse;
import com.eureka.sensationserver.dto.common.Msg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity defaultException(Exception e){
        return new ResponseEntity<>(new MessageResponse("Bad Request"), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAuthenticatedException.class)
    public ResponseEntity notAuthenticatedException(NotAuthenticatedException e){
        return new ResponseEntity(new MessageResponse(Msg.NOTAUTHENTICATED), null, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity duplicationException(DuplicateException e){
        return new ResponseEntity(new MessageResponse(Msg.DUPLICATION), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity badCredentialsException(BadCredentialsException e){
        return new ResponseEntity(new MessageResponse(Msg.BADCREDENTIALS), null, HttpStatus.BAD_REQUEST);
    }
}
