package com.example.jpa_board.entity;

import com.example.jpa_board.dto.board.BoardRequestDto;
import com.example.jpa_board.dto.member.MemberRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public Board(BoardRequestDto requestDto, Member member) {
        this.member = member;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

}
