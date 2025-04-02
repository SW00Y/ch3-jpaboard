package com.example.jpa_board.service.member;


import com.example.jpa_board.dto.member.MemberRequestDto;
import com.example.jpa_board.dto.member.MemberResponseDto;
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
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return new MemberResponseDto(member);
    }

    public Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return member;
    }

    @Transactional
    public MemberResponseDto saveMember(MemberRequestDto memberRequestDto) {
        Member member = new Member(memberRequestDto.getUsername(), memberRequestDto.getPassword(), memberRequestDto.getEmail());
        memberRepository.save(member);
        return new MemberResponseDto(member);
    }

    @Transactional
    public MemberResponseDto updateMember(Long memberId, String email) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        member.updateEmail(email);
        return new MemberResponseDto(member);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        memberRepository.delete(member);
    }
}
