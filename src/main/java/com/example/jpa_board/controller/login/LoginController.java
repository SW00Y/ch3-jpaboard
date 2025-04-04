package com.example.jpa_board.controller.login;

import com.example.jpa_board.config.security.PasswordEncoder;
import com.example.jpa_board.dto.member.LoginRequestDto;
import com.example.jpa_board.dto.member.MemberResponseDto;
import com.example.jpa_board.entity.Member;
import com.example.jpa_board.repository.member.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Authentication API", description = "로그인 및 로그아웃 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "로그인", description = "사용자 이메일, 비밀번호로 로그인 성공 시 쿠키 설정")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        Optional<Member> optionalMember = memberRepository.findByEmail(requestDto.getEmail());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
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

    @Operation(summary = "로그아웃", description = "로그아웃시 쿠키 제거")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie idCookie = new Cookie("memberId", null);
        Cookie usernameCookie = new Cookie("username", null);
        Cookie emailCookie = new Cookie("email", null);

        idCookie.setMaxAge(0);
        usernameCookie.setMaxAge(0);
        emailCookie.setMaxAge(0);

        idCookie.setPath("/");
        usernameCookie.setPath("/");
        emailCookie.setPath("/");

        response.addCookie(idCookie);
        response.addCookie(usernameCookie);
        response.addCookie(emailCookie);

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

}
