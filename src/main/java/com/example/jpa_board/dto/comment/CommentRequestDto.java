package com.example.jpa_board.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
@Getter
public class CommentRequestDto {
    private final String content;

    public CommentRequestDto(String content) {
        this.content = content;
    }
}
