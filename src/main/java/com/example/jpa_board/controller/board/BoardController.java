package com.example.jpa_board.controller.board;

import com.example.jpa_board.dto.board.BoardRequestDto;
import com.example.jpa_board.dto.board.BoardResponseDto;
import com.example.jpa_board.entity.Member;
import com.example.jpa_board.service.board.BoardService;
import com.example.jpa_board.service.member.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto
    , HttpServletRequest httpServletRequest) {

        Long memberId = getMemberIdFromCookie(httpServletRequest);
        Member member = memberService.getMember(memberId);

        return ResponseEntity.ok(boardService.createBoard(boardRequestDto, member));
    }

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

    private Long getMemberIdFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("memberId")) {
                    return Long.parseLong(cookie.getValue());
                }
            }
        }
        return null;
    }
}
