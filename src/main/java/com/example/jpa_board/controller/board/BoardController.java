package com.example.jpa_board.controller.board;

import com.example.jpa_board.dto.board.BoardListResponseDto;
import com.example.jpa_board.dto.board.BoardRequestDto;
import com.example.jpa_board.dto.board.BoardResponseDto;
import com.example.jpa_board.entity.Member;
import com.example.jpa_board.service.board.BoardService;
import com.example.jpa_board.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Tag(name = "Board API", description = "게시글 API")
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시글 목록 조회", description = "모든 게시글을 조회")
    @GetMapping
    public List<BoardResponseDto> getBoard() {
        return boardService.getAllBoard();
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글 ID를 기준 게시글 조회")
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable long boardId) {
        BoardResponseDto dto = boardService.getBoardById(boardId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "게시글 목록 (페이징)", description = "페이지 번호와 페이지 크기를 기준 게시글 목록 조회")
    @GetMapping("/page/{pageNumber}")
    public List<BoardListResponseDto> getBoards(@Parameter(description = "페이지 번호 (0부터 시작)") @PathVariable int pageNumber, @Parameter(description = "페이지 크기 (기본값: 10)") @RequestParam(defaultValue = "10") int size) {
        return boardService.getBoards(pageNumber, size);
    }

    @Operation(summary = "게시글 생성", description = "게시글 생성")
    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto, @CookieValue("memberId") long memberId) {
        BoardResponseDto dto = boardService.createBoard(boardRequestDto, memberId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "게시글 수정", description = "게시글 ID, 쿠키의 MemberId로 게시글 수정")
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable long boardId, @RequestBody BoardRequestDto boardRequestDto, @CookieValue("memberId") long memberId) {
        BoardResponseDto dto = boardService.updateBoard(boardId, boardRequestDto, memberId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "게시글 삭제", description = "게시글 ID, 쿠키의 MemberId로 게시글 삭제")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable long boardId, @CookieValue("memberId") long memberId) {
        boardService.deleteBoard(boardId, memberId);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }


}
