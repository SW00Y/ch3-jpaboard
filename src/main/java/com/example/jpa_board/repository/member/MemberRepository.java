package com.example.jpa_board.repository.member;

import com.example.jpa_board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Board, Long> {

}
