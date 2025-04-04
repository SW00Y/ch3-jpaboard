package com.example.jpa_board.config.exception;

import org.springframework.http.HttpStatus;

/**
 * 에러코드를 한곳에서 관리하기 위한 Enum
 */
public enum ExceptionErrorCode {
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다.", "BOARD-001"),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "본인이 작성한 글만 수정 가능합니다.", "AUTH-001"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디 혹은 비밀번호가 틀렸습니다.", "AUTH-002"),
    TITLE_LENGTH_SHORT(HttpStatus.BAD_REQUEST, "글 제목이 너무 짧습니다.", "BOARD-003"),
    CONTENT_IS_NULL(HttpStatus.BAD_REQUEST, "내용이 없습니다.", "BOARD-004"),
    EMAIL_VALUE_ERROR(HttpStatus.BAD_REQUEST, "메일 주소가 잘못되었습니다.", "MEMBER-001"),
    USERNAME_LENGTH_SHORT(HttpStatus.BAD_REQUEST, "유저명이 너무 짧습니다.", "MEMBER-002"),
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
