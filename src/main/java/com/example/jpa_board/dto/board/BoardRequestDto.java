package com.example.jpa_board.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BoardRequestDto {
    long memberId;
    String title;
    String contents;
    String password;
}
