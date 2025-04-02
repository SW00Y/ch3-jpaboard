package com.example.jpa_board.repository.member;

import com.example.jpa_board.entity.Board;
import com.example.jpa_board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
