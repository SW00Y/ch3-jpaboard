package com.example.jpa_board.dto.board;

import com.example.jpa_board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class BoardResponseDto {
    private long boardId;
    private String username;
    private String title;
    private String contents;

    public BoardResponseDto(Board board) {
        this.boardId = board.getId();
        this.username = board.getMember().getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
    }
}


