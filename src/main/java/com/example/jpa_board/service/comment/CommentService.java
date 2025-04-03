package com.example.jpa_board.service.comment;

import com.example.jpa_board.dto.comment.CommentRequestDto;
import com.example.jpa_board.dto.comment.CommentResponseDto;
import com.example.jpa_board.entity.Board;
import com.example.jpa_board.entity.Comment;
import com.example.jpa_board.entity.Member;
import com.example.jpa_board.config.exception.CustomException;
import com.example.jpa_board.config.exception.ExceptionErrorCode;
import com.example.jpa_board.repository.board.BoardRepository;
import com.example.jpa_board.repository.comment.CommentRepository;
import com.example.jpa_board.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public CommentResponseDto createComment(Long boardId, Long memberId, CommentRequestDto requestDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.BOARD_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.USER_NOT_FOUND));

        Comment comment = new Comment(board, member ,requestDto);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getComments(Long boardId) {
        List<CommentResponseDto> dto = commentRepository.findByBoardId(boardId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return dto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, Long memberId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getMember().getId().equals(memberId)) {
            throw new CustomException(ExceptionErrorCode.UNAUTHORIZED_ACCESS);
        }

        comment.setContent(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getMember().getId().equals(memberId)) {
            throw new CustomException(ExceptionErrorCode.UNAUTHORIZED_ACCESS);
        }

        commentRepository.delete(comment);
    }
}
