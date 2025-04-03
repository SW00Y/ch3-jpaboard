package com.example.jpa_board.dto.board;

import com.example.jpa_board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardListResponseDto {
    private final Long id;
    private final String title;
    private final String username;
    private final int commentCount;
    private final LocalDateTime addLog;
    private final LocalDateTime uppLog;

    public BoardListResponseDto(Board board, int commentCount) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getMember().getUsername();
        this.commentCount = commentCount;
        this.addLog = board.getAddLog();
        this.uppLog = board.getUppLog();
    }
}
