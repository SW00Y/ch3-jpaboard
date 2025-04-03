package com.example.jpa_board.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BoardRequestDto {
    private long memberId;
    private String title;
    private String contents;
}
