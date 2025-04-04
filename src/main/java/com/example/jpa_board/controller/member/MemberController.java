package com.example.jpa_board.controller.member;

import com.example.jpa_board.dto.member.MemberRequestDto;
import com.example.jpa_board.dto.member.MemberResponseDto;
import com.example.jpa_board.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member API", description = "사용자 API")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 정보 조회", description = "사용자의 ID로 회원 정보 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getMemberById(
            @Parameter(description = "사용자 Id")
            @PathVariable Long memberId) {
        MemberResponseDto dto = memberService.getMemberById(memberId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "회원 가입", description = "사용자 등록")
    @PostMapping
    public ResponseEntity<MemberResponseDto> saveMember(@RequestBody MemberRequestDto memberRequestDto) {

        MemberResponseDto dto = memberService.saveMember(memberRequestDto);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "회원 정보 수정", description = "사용자 ID, 쿠키 사용자 ID가 일치하는 경우 이름 업데이트")
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateMember(@Parameter(description = "사용자 Id")
                                                          @PathVariable Long memberId,
                                                          @RequestBody MemberRequestDto memberRequestDto,
                                                          @CookieValue("memberId") long cookieMemberId) {
        MemberResponseDto dto = memberService.updateMember(memberId, memberRequestDto, cookieMemberId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "회원 삭제", description = "사용자 ID, 쿠키 사용자 ID가 일치하는 경우 사용자 삭제")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> deleteMember(@Parameter(description = "사용자 Id")
                                               @PathVariable Long memberId, @CookieValue("memberId") long cookieMemberId) {
        memberService.deleteMember(memberId, cookieMemberId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
