package com.example.SpringSecurity.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleAll(RuntimeException ex) {
        log.error("Error: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), "Non Authorized Access");
        return new ResponseEntity<>(error, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

}
