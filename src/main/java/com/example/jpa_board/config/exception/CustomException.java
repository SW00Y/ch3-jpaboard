package com.example.jpa_board.config.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    public CustomException(ExceptionErrorCode exceptionErrorCode) {

        super(exceptionErrorCode.getMessage());
        this.status = exceptionErrorCode.getStatus();
        this.errorCode = exceptionErrorCode.getCode();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
