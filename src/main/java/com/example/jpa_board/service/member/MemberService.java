package com.example.jpa_board.service.member;


import com.example.jpa_board.config.exception.CustomException;
import com.example.jpa_board.config.exception.ExceptionErrorCode;
import com.example.jpa_board.dto.member.MemberRequestDto;
import com.example.jpa_board.dto.member.MemberResponseDto;
import com.example.jpa_board.entity.Board;
import com.example.jpa_board.entity.Member;
import com.example.jpa_board.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

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
        Member member = new Member(memberRequestDto.getUsername(), memberRequestDto.getPassword(), memberRequestDto.getEmail());
        memberRepository.save(member);
        return new MemberResponseDto(member);
    }

    @Transactional
    public MemberResponseDto updateMember(Long memberId, MemberRequestDto memberRequestDto, Long cookieMemberId) {
        Member member = checkUpdateMember(memberId, cookieMemberId, memberRequestDto);
        member.updateUsername(memberRequestDto.getUsername());
        return new MemberResponseDto(member);
    }

    @Transactional
    public void deleteMember(Long memberId, Long cookieMemberId) {
        Member member = checkDeleteMember(memberId, cookieMemberId);
        memberRepository.delete(member);
    }

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


}
