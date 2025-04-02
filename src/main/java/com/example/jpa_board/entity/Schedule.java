package com.example.jpa_board.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "board")
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;

}
