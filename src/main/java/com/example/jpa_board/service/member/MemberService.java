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

    public MemberResponseDto getMemberById(Long memberId) {
        Member member = checkMember(memberId);
        return new MemberResponseDto(member);
    }

    public Member getMember(Long memberId) {
        Member member = checkMember(memberId);
        return member;
    }

    @Transactional
    public MemberResponseDto saveMember(MemberRequestDto memberRequestDto) {
        System.out.printf("memberRequestDto.email: %s\n", memberRequestDto.getEmail());
        System.out.printf("memberRequestDto.password: %s\n", memberRequestDto.getPassword());
        System.out.println("memberRequestDto.username: " + memberRequestDto.getUsername());
        checkUserName(memberRequestDto);
        checkEmail(memberRequestDto);
        String encodePwd = passwordEncoder.encode(memberRequestDto.getPassword());
        Member member = new Member(memberRequestDto.getUsername(), encodePwd, memberRequestDto.getEmail());
        memberRepository.save(member);
        return new MemberResponseDto(member);
    }

    @Transactional
    public MemberResponseDto updateMember(Long memberId, MemberRequestDto memberRequestDto, Long cookieMemberId) {
        checkUserName(memberRequestDto);
        Member member = checkUpdateMember(memberId, cookieMemberId, memberRequestDto);
        member.updateUsername(memberRequestDto.getUsername());
        return new MemberResponseDto(member);
    }

    @Transactional
    public void deleteMember(Long memberId, Long cookieMemberId) {
        Member member = checkDeleteMember(memberId, cookieMemberId);
        memberRepository.delete(member);
    }

    /**********
     * 예외처리관련
     * @param memberId
     * @return
     */

    public Member checkMember(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.USER_NOT_FOUND));

        return member;
    }


    public Member checkUpdateMember(long memberId, long cookieMemberId, MemberRequestDto memberRequestDto) {
        if(memberId != cookieMemberId)
        {
            throw new CustomException(ExceptionErrorCode.USER_NOT_FOUND);
        }



        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(memberRequestDto.getPassword(), member.getPassword()))
        {
            throw new CustomException(ExceptionErrorCode.USER_NOT_FOUND);
        };



        return member;
    }

    public Member checkDeleteMember(long memberId, long cookieMemberId) {
        if(memberId != cookieMemberId)
        {
            throw new CustomException(ExceptionErrorCode.USER_NOT_FOUND);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.USER_NOT_FOUND));

        return member;
    }

    public void checkUserName(MemberRequestDto memberRequestDto){
        if (memberRequestDto.getUsername().length() < 5) {
            throw new CustomException(ExceptionErrorCode.USERNAME_LENGTH_SHORT);
        }
    }

    public void checkEmail(MemberRequestDto memberRequestDto){
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.matches(EMAIL_REGEX, memberRequestDto.getEmail())) {
            throw new CustomException(ExceptionErrorCode.EMAIL_VALUE_ERROR);
        }
    }




}
