package com.example.jpa_board.config.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionErrorCode {
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글없음", "BOARD-001"),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "본인거만수정삭제가능", "AUTH-001"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "아디비번틀림", "AUTH-002"),
    TITLE_LENGTH_SHORT(HttpStatus.BAD_REQUEST, "글제목너무짧음","BOARD-003"),
    CONTENT_IS_NULL(HttpStatus.BAD_REQUEST, "글내용없음","BOARD-004"),
    EMAIL_VALUE_ERROR(HttpStatus.BAD_REQUEST, "메일주소이상함","MEMBER-001"),
    USERNAME_LENGTH_SHORT(HttpStatus.BAD_REQUEST, "유저명짧음","MEMBER-002"),
    REQUEST_DTO_ERROR_PWD_NULL(HttpStatus.BAD_REQUEST, "비밀번호는 필수값 입니다.", "MEMBER-003"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.", "COMMENT-001");


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
