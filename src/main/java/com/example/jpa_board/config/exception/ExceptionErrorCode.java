package com.example.jpa_board.config.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionErrorCode {
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글없음", "BOARD-001"),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "본인거만수정삭제가능", "BOARD-002"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "아디비번틀림", "MEMBER-002"),
    REQUEST_DTO_ERROR_PWD_NULL(HttpStatus.BAD_REQUEST, "비밀번호는 필수값 입니다.", "MEMBER-005");

    private final HttpStatus status;
    private final String message;
    private final String code;

    ExceptionErrorCode(HttpStatus status, String message, String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
