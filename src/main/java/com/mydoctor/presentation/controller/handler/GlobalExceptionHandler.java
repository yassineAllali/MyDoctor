package com.mydoctor.presentation.controller.handler;

import com.fasterxml.jackson.databind.DatabindException;
import com.mydoctor.application.exception.BusinessException;
import com.mydoctor.application.exception.IllegalArgumentException;
import com.mydoctor.application.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        log.error(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(BusinessException ex) {
        log.error(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Illegal Argument : " + ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({DatabindException.class})
    public ResponseEntity<String> handleDataBindingExceptions(DatabindException ex) {
        log.error(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mal formated json request!");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        log.error(ex.getMessage(), ex.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
    }
}

