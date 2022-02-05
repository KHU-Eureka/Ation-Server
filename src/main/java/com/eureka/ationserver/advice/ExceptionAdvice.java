package com.eureka.ationserver.advice;

import com.eureka.ationserver.advice.exception.CommonException;
import com.eureka.ationserver.advice.exception.DuplicateException;
import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.advice.exception.UnAuthorizedException;
import com.eureka.ationserver.dto.common.MessageResponse;
import com.eureka.ationserver.dto.common.Msg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity defaultException(Exception e) {
    return new ResponseEntity<>(new MessageResponse(e.toString()), null, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnAuthorizedException.class)
  public ResponseEntity UnAuthorizedException(UnAuthorizedException e) {
    return new ResponseEntity(new MessageResponse(Msg.NOTAUTHENTICATED), null,
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(DuplicateException.class)
  public ResponseEntity duplicationException(DuplicateException e) {
    return new ResponseEntity(new MessageResponse(Msg.DUPLICATION), null, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity badCredentialsException(BadCredentialsException e) {
    return new ResponseEntity(new MessageResponse(Msg.BADCREDENTIALS), null,
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity entityNotFoundException(EntityNotFoundException e) {
    return new ResponseEntity(new MessageResponse(Msg.DATANOTFOUND), null, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity forbiddenException(ForbiddenException e) {
    return new ResponseEntity(new MessageResponse(Msg.FORBIDDEN), null, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(CommonException.class)
  public ResponseEntity commonException(CommonException e) {
    return new ResponseEntity(new MessageResponse(e.getMessage()), null, HttpStatus.BAD_REQUEST);
  }
}
