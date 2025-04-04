package com.example.jpa_board.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice   //전역 예외처리
public class GlobalExceptionHandler extends Exception {

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handle(CustomException e) {

        ExceptionDto exceptionDto = new ExceptionDto(e.getStatus().value(), e.getErrorCode(), e.getMessage());

        return ResponseEntity.status(e.getStatus()).body(exceptionDto);
    }

}
