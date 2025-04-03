package com.example.jpa_board.controller.member;

import com.example.jpa_board.dto.member.MemberRequestDto;
import com.example.jpa_board.dto.member.MemberResponseDto;
import com.example.jpa_board.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable Long memberId) {
        MemberResponseDto dto = memberService.getMemberById(memberId);
        return ResponseEntity.ok(dto);
    }

    // 사용자 저장
    @PostMapping
    public ResponseEntity<MemberResponseDto> saveMember(@RequestBody MemberRequestDto memberRequestDto) {

        MemberResponseDto dto = memberService.saveMember(memberRequestDto);
        return ResponseEntity.ok(dto);
    }

    // 이름 업데이트
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable Long memberId,
                                                          @RequestBody MemberRequestDto memberRequestDto,
                                                          @CookieValue("memberId") long cookieMemberId) {
        MemberResponseDto dto = memberService.updateMember(memberId, memberRequestDto, cookieMemberId);
        return ResponseEntity.ok(dto);
    }

    // 사용자 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId, @CookieValue("memberId") long cookieMemberId) {
        memberService.deleteMember(memberId, cookieMemberId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
