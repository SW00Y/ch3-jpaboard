package com.example.jpa_board.dto.member;

import com.example.jpa_board.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
//@AllArgsConstructor
public class MemberResponseDto {
    private final Long id;
    private final String username;
    private final String email;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.email = member.getEmail();
    }
}
