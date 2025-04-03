package com.example.jpa_board.controller.board;

import com.example.jpa_board.dto.board.BoardListResponseDto;
import com.example.jpa_board.dto.board.BoardRequestDto;
import com.example.jpa_board.dto.board.BoardResponseDto;
import com.example.jpa_board.entity.Member;
import com.example.jpa_board.service.board.BoardService;
import com.example.jpa_board.service.member.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.http.client.HttpClientProperties;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final HttpClientProperties httpClientProperties;


    @GetMapping
    public List<BoardResponseDto> getBoard() {
        return boardService.getAllBoard();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable long boardId) {
            BoardResponseDto dto = boardService.getBoardById(boardId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/page/{pageNumber}")
    public List<BoardListResponseDto> getBoards(
            @PathVariable int pageNumber,
            @RequestParam(defaultValue = "10") int size) {
        return boardService.getBoards(pageNumber, size);
    }

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto, @CookieValue("memberId") long memberId) {
        BoardResponseDto dto = boardService.createBoard(boardRequestDto, memberId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(
            @PathVariable long boardId, @RequestBody BoardRequestDto boardRequestDto, @CookieValue("memberId") long memberId) {
        BoardResponseDto dto = boardService.updateBoard(boardId, boardRequestDto, memberId);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable long boardId, @CookieValue("memberId") long memberId) {
        boardService.deleteBoard(boardId, memberId);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }


}
