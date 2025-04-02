package com.example.jpa_board.repository.board;

import com.example.jpa_board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
