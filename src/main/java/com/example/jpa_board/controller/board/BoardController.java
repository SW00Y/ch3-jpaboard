package com.example.jpa_board.controller.board;

import com.example.jpa_board.dto.board.BoardRequestDto;
import com.example.jpa_board.dto.board.BoardResponseDto;
import com.example.jpa_board.entity.Member;
import com.example.jpa_board.service.board.BoardService;
import com.example.jpa_board.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;


    @GetMapping
    public List<BoardResponseDto> getBoard() {
        return boardService.getAllBoard();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable long boardId) {
        return ResponseEntity.ok(boardService.getBoardById(boardId));
    }

    //save 할때 member부분은 cookie의 memberid 가져오기

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(
            @PathVariable long boardId, @RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.ok(boardService.updateBoard(boardId, boardRequestDto));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }
}
