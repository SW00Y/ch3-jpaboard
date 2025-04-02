package com.example.jpa_board.service.board;


import com.example.jpa_board.dto.board.BoardRequestDto;
import com.example.jpa_board.dto.board.BoardResponseDto;
import com.example.jpa_board.entity.Board;
import com.example.jpa_board.entity.Member;
import com.example.jpa_board.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    //로그인을 한 상태에서 쿠키에 있는 memberId 와 Board의 memberId가 같을 경우 upp, del 가능하게 처리

    public List<BoardResponseDto> getAllBoard() {
        List<Board> result = boardRepository.findAll();

        List<BoardResponseDto> dto = result.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        return dto;
    }

    public BoardResponseDto getBoardById(long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("ById에러"));
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, Member member) {
        Board board = new Board(boardRequestDto,member);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(long boardId, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("update에러"));
        board.update(boardRequestDto);
        return new BoardResponseDto(board);
    }

    @Transactional
    public void deleteBoard(long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("delete에러"));
        boardRepository.delete(board);
    }


}
