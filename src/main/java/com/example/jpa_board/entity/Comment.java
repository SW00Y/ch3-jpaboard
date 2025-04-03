package com.example.jpa_board.entity;

import com.example.jpa_board.dto.board.BoardRequestDto;
import com.example.jpa_board.dto.comment.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column
    private String content;

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public Comment(Board board, Member member, CommentRequestDto requestDto) {
        this.member = member;
        this.board = board;
        this.content = requestDto.getContent();
    }

}
