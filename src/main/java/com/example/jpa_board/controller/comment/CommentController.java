package com.example.jpa_board.controller.comment;

import com.example.jpa_board.dto.comment.CommentRequestDto;
import com.example.jpa_board.dto.comment.CommentResponseDto;
import com.example.jpa_board.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment API", description = "댓글 API")
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "특정 게시글의 댓글 조회", description = "게시글 ID 기준으로 해당 게시글의 모든 댓글 조회")
    @GetMapping("/{boardId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@Parameter(description = "게시글 ID") @PathVariable Long boardId) {
        List<CommentResponseDto> dto = commentService.getComments(boardId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "댓글 작성", description = "특정 게시글에 댓글 작성")
    @PostMapping("/{boardId}")
    public ResponseEntity<CommentResponseDto> createComment(@Parameter(description = "게시글 ID") @PathVariable Long boardId, @RequestBody CommentRequestDto requestDto, @CookieValue("memberId") long memberId) {
        CommentResponseDto dto = commentService.createComment(boardId, memberId, requestDto);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "댓글 수정", description = "댓글 ID, 쿠키의 MemberId로 댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@Parameter(description = "댓글 ID") @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @CookieValue("memberId") long memberId) {
        CommentResponseDto dto = commentService.updateComment(commentId, memberId, requestDto);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "댓글 삭제", description = "댓글 ID, 쿠키의 MemberId로 댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @CookieValue("memberId") long memberId) {

        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }

}