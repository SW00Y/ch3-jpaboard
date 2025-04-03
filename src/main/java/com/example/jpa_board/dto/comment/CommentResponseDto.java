package com.example.jpa_board.dto.comment;

import com.example.jpa_board.entity.Board;
import com.example.jpa_board.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private long id;
    private String username;
    private String content;
    private LocalDateTime addTime;
    private LocalDateTime uppTime;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getMember().getUsername();
        this.content = comment.getContent();
        this.addTime = comment.getAddLog();
        this.uppTime = comment.getUppLog();
    }
}


