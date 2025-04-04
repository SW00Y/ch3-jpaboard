package com.example.jpa_board.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor
public class LoginRequestDto {
    private final String email;
    private final String password;

    LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
