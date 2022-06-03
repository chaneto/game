package com.example.game.web;

import java.time.LocalDate;
import com.example.game.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorMessage> handleValidationException(ValidationException ex) {
    ErrorMessage message = new ErrorMessage(
      HttpStatus.BAD_REQUEST.toString(),
      LocalDate.now(),
      "Validation fields errors!!!",
      ex.getMessage());
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UsernameException.class)
  public ResponseEntity<ErrorMessage> usernameException(UsernameException ex) {
    ErrorMessage message = new ErrorMessage(
      HttpStatus.CONFLICT.toString(),
      LocalDate.now(),
      "Username errors!!!",
      ex.getMessage());
    return new ResponseEntity<>(message, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorMessage> usernameException(NotFoundException ex) {
    ErrorMessage message = new ErrorMessage(
      HttpStatus.NOT_FOUND.toString(),
      LocalDate.now(),
      "Not found!!!",
      ex.getMessage());
    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(LoginException.class)
  public ResponseEntity<ErrorMessage> loginException(LoginException ex) {
    ErrorMessage message = new ErrorMessage(
      HttpStatus.BAD_REQUEST.toString(),
      LocalDate.now(),
      "Login exception!!!",
      ex.getMessage());
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorMessage> unauthorizedException(UnauthorizedException ex) {
    ErrorMessage message = new ErrorMessage(
      HttpStatus.UNAUTHORIZED.toString(),
      LocalDate.now(),
      "Authorized exception!!!",
      ex.getMessage());
    return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
  }
}
