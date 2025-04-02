package com.example.jpa_board.controller.login;

import com.example.jpa_board.dto.member.LoginRequestDto;
import com.example.jpa_board.dto.member.MemberResponseDto;
import com.example.jpa_board.entity.Member;
import com.example.jpa_board.repository.member.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        Optional<Member> optionalMember = memberRepository.findByEmail(requestDto.getEmail());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getPassword().equals(requestDto.getPassword())) { 
                Cookie idCookie = new Cookie("memberId", String.valueOf(member.getId()));
                Cookie usernameCookie = new Cookie("username", member.getUsername());
                Cookie emailCookie = new Cookie("email", member.getEmail());

                idCookie.setPath("/");
                usernameCookie.setPath("/");
                emailCookie.setPath("/");

                response.addCookie(idCookie);
                response.addCookie(usernameCookie);
                response.addCookie(emailCookie);

                return ResponseEntity.ok(new MemberResponseDto(member));
            }
        }
        return ResponseEntity.status(401).body("아이디나 비밀번호 잘못됨");
    }
}
