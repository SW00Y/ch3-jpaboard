package com.example.jpa_board.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor
//@AllArgsConstructor
public class MemberRequestDto {
    private final String username;
    private final String password;
    private final String email;

    MemberRequestDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
