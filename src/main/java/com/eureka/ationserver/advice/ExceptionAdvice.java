package com.eureka.ationserver.advice;

import com.eureka.ationserver.advice.exception.CommonException;
import com.eureka.ationserver.advice.exception.DuplicateException;
import com.eureka.ationserver.advice.exception.ForbiddenException;
import com.eureka.ationserver.advice.exception.ResourceNotFoundException;
import com.eureka.ationserver.advice.exception.UnAuthorizedException;
import com.eureka.ationserver.dto.error.ErrorResponse;
import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(Exception.class)
  public ErrorResponse.Out defaultException(Exception e) {
    return ErrorResponse.toOut(e.getMessage());
  }

  @ExceptionHandler(UnAuthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse.Out UnAuthorizedException(UnAuthorizedException e) {
    return ErrorResponse.toOut(e.getMessage());
  }

  @ExceptionHandler(DuplicateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse.Out duplicationException(DuplicateException e) {
    return ErrorResponse.toOut(e.getMessage());
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse.Out badCredentialsException(BadCredentialsException e) {
    return ErrorResponse.toOut(e.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse.Out entityNotFoundException(EntityNotFoundException e) {
    return ErrorResponse.toOut(e.getMessage());
  }

  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorResponse.Out forbiddenException(ForbiddenException e) {
    return ErrorResponse.toOut(e.getMessage());
  }

  @ExceptionHandler(CommonException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse.Out commonException(CommonException e) {
    return ErrorResponse.toOut(e.getMessage());
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse.Out resourceNotFoundException(ResourceNotFoundException e) {
    return ErrorResponse.toOut(e.getMessage());
  }
}
