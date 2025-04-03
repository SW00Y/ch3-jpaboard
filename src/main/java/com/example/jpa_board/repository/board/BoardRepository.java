package com.example.jpa_board.repository.board;

import com.example.jpa_board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT a, COUNT(b) FROM Board a LEFT JOIN Comment b ON a.id = b.board.id " +
            "GROUP BY a ORDER BY a.uppLog DESC")
    Page<Object[]> findAllWithCommentCount(Pageable pageable);

}
