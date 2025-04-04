package com.example.jpa_board.service.board;


import com.example.jpa_board.config.exception.CustomException;
import com.example.jpa_board.config.exception.ExceptionErrorCode;
import com.example.jpa_board.dto.board.BoardListResponseDto;
import com.example.jpa_board.dto.board.BoardRequestDto;
import com.example.jpa_board.dto.board.BoardResponseDto;
import com.example.jpa_board.entity.Board;
import com.example.jpa_board.entity.Member;
import com.example.jpa_board.repository.board.BoardRepository;
import com.example.jpa_board.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    //Board 전체 출력
    public List<BoardResponseDto> getAllBoard() {
        List<Board> result = boardRepository.findAll();

        List<BoardResponseDto> dto = result.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        return dto;
    }

    //Board Id로 조회
    public BoardResponseDto getBoardById(long boardId) {
        Board board = checkBoardId(boardId);
        return new BoardResponseDto(board);
    }

    //Board Page
    public List<BoardListResponseDto> getBoards(int page, int size) {
        int safePage = page < 0 ? 0 : page; // 해설 강의 듣고 추가 .. 안전을 위한 코드
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> boardPage = boardRepository.findAllWithCommentCount(pageable);

        //row[0] = board(a), row[1] = count(b) = comment
        return boardPage.stream()
                .map(row -> new BoardListResponseDto((Board) row[0], ((Number) row[1]).intValue()))
                .toList();
    }

    //Board 생성, 현재 로그인한(쿠키) memberId 사용
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, Long memberId) {
        checkBoardContent(boardRequestDto);
        Member member = memberService.getMember(memberId);
        Board board = new Board(boardRequestDto, member);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    //Board 업데이트, 현재 로그인한(쿠키) memberId 사용
    @Transactional
    public BoardResponseDto updateBoard(long boardId, BoardRequestDto boardRequestDto, Long memberId) {
        checkBoardContent(boardRequestDto);
        Board board = checkBoardOwner(boardId, memberId);
        board.update(boardRequestDto);
        return new BoardResponseDto(board);
    }

    //Board 삭제, 현재 로그인한(쿠키) memberId 사용
    @Transactional
    public void deleteBoard(long boardId, Long memberId) {
        Board board = checkBoardOwner(boardId, memberId);
        boardRepository.delete(board);
    }


    /**************
     * 예외처리부분
     * checkBoardOwner
     * checkBoardId
     * checkBoardContent
     */

    //boardId,와 로그인한(쿠키) memberId가 일치하는지 검사
    public Board checkBoardOwner(long boardId, long memberId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.BOARD_NOT_FOUND));

        if (!board.getMember().getId().equals(memberId)) {
            throw new CustomException(ExceptionErrorCode.UNAUTHORIZED_ACCESS);
        }

        return board;
    }

    //boardId가 존재하는지 검사
    public Board checkBoardId(long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.BOARD_NOT_FOUND));

        return board;
    }

    //Board 생성 시 유효성 검사
    public void checkBoardContent(BoardRequestDto boardRequestDto) {
        if (boardRequestDto.getTitle().length() > 30) {
            throw new CustomException(ExceptionErrorCode.TITLE_LENGTH_SHORT);
        }

        if (boardRequestDto.getContents() == null) {
            throw new CustomException(ExceptionErrorCode.CONTENT_IS_NULL);
        }
    }

}
