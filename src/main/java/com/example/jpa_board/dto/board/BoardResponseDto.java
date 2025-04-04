package com.example.jpa_board.dto.board;

import com.example.jpa_board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
//@Setter
public class BoardResponseDto {
    private final long boardId;
    private final String username;
    private final String title;
    private final String contents;

    public BoardResponseDto(Board board) {
        this.boardId = board.getId();
        this.username = board.getMember().getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
    }
}


