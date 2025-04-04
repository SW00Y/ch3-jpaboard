package com.example.jpa_board.service.member;


import com.example.jpa_board.config.security.PasswordEncoder;
import com.example.jpa_board.config.exception.CustomException;
import com.example.jpa_board.config.exception.ExceptionErrorCode;
import com.example.jpa_board.dto.member.MemberRequestDto;
import com.example.jpa_board.dto.member.MemberResponseDto;
import com.example.jpa_board.entity.Member;
import com.example.jpa_board.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //MemberId로 MemberDto 반환
    public MemberResponseDto getMemberById(Long memberId) {
        Member member = checkMember(memberId);
        return new MemberResponseDto(member);
    }

    //MemberId로 Member 반환(내부사용)
    public Member getMember(Long memberId) {
        Member member = checkMember(memberId);
        return member;
    }

    //Member 생성
    @Transactional
    public MemberResponseDto saveMember(MemberRequestDto memberRequestDto) {
        checkUserName(memberRequestDto);    //유효성검사
        checkEmail(memberRequestDto);       //유효성검사
        String encodePwd = passwordEncoder.encode(memberRequestDto.getPassword());
        Member member = new Member(memberRequestDto.getUsername(), encodePwd, memberRequestDto.getEmail());
        memberRepository.save(member);
        return new MemberResponseDto(member);
    }

    //Member Update - 로그인한(쿠키) MemberId 검사
    @Transactional
    public MemberResponseDto updateMember(Long memberId, MemberRequestDto memberRequestDto, Long cookieMemberId) {
        checkUserName(memberRequestDto);
        Member member = checkUpdateMember(memberId, cookieMemberId, memberRequestDto);
        member.updateUsername(memberRequestDto.getUsername());
        return new MemberResponseDto(member);
    }

    //Member delete- 로그인한(쿠키) MemberId 검사
    @Transactional
    public void deleteMember(Long memberId, Long cookieMemberId) {
        Member member = checkDeleteMember(memberId, cookieMemberId);
        memberRepository.delete(member);
    }

    /**********
     * 예외처리부분
     * checkMember
     * checkUpdateMember
     * checkDeleteMember
     * checkUserName
     * checkEmail
     */

    //Id로 조회한 Member가 존재하는지 확인
    public Member checkMember(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.USER_NOT_FOUND));

        return member;
    }

    //MemberUpdate 시 CookieMemberId, memberId 일치 검사
    public Member checkUpdateMember(long memberId, long cookieMemberId, MemberRequestDto memberRequestDto) {
        if (memberId != cookieMemberId) {
            throw new CustomException(ExceptionErrorCode.USER_NOT_FOUND);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(memberRequestDto.getPassword(), member.getPassword())) {
            throw new CustomException(ExceptionErrorCode.USER_NOT_FOUND);
        }
        ;

        return member;
    }

    //MemberDelete 시 CookieMemberId, memberId 일치 검사
    public Member checkDeleteMember(long memberId, long cookieMemberId) {
        if (memberId != cookieMemberId) {
            throw new CustomException(ExceptionErrorCode.USER_NOT_FOUND);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.USER_NOT_FOUND));

        return member;
    }

    //userName 유효성 검사
    public void checkUserName(MemberRequestDto memberRequestDto) {
        if (memberRequestDto.getUsername().length() < 5) {
            throw new CustomException(ExceptionErrorCode.USERNAME_LENGTH_SHORT);
        }
    }

    //Email 정규식 검사
    public void checkEmail(MemberRequestDto memberRequestDto) {
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.matches(EMAIL_REGEX, memberRequestDto.getEmail())) {
            throw new CustomException(ExceptionErrorCode.EMAIL_VALUE_ERROR);
        }
    }


}
