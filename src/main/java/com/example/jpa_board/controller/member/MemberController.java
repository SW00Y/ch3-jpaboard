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
        MemberResponseDto member = memberService.getMemberById(memberId);
        return ResponseEntity.ok(member);
    }

    // 사용자 저장
    @PostMapping
    public ResponseEntity<MemberResponseDto> saveMember(@RequestBody MemberRequestDto memberRequestDto) {
        MemberResponseDto savedMember = memberService.saveMember(memberRequestDto);
        return ResponseEntity.ok(savedMember);
    }

    // 이메일 업데이트
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable Long memberId, @RequestBody String email) {
        MemberResponseDto updatedMember = memberService.updateMember(memberId, email);
        return ResponseEntity.ok(updatedMember);
    }

    // 사용자 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }
}
