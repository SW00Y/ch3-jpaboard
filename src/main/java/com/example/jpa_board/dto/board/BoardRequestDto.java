package com.example.jpa_board.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
//@NoArgsConstructor
@Getter
public class BoardRequestDto {
    private final long memberId;
    private final String title;
    private final String contents;

    public BoardRequestDto(long memberId, String title, String contents) {
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
    }
}
