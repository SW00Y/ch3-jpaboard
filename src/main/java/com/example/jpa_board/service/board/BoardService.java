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

    public List<BoardResponseDto> getAllBoard() {
        List<Board> result = boardRepository.findAll();

        List<BoardResponseDto> dto = result.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        return dto;
    }

    public BoardResponseDto getBoardById(long boardId) {
        Board board = checkBoardId(boardId);
        return new BoardResponseDto(board);
    }

    public List<BoardListResponseDto> getBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> boardPage = boardRepository.findAllWithCommentCount(pageable);

        //row[0] = board(a)
        //row[1] = count(b) = comment
        return boardPage.stream()
                .map(row -> new BoardListResponseDto((Board) row[0], ((Number) row[1]).intValue()))
                .toList();
    }

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, Long memberId) {
        checkBoardContent(boardRequestDto);
        Member member = memberService.getMember(memberId);
        Board board = new Board(boardRequestDto, member);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(long boardId, BoardRequestDto boardRequestDto, Long memberId) {
        checkBoardContent(boardRequestDto);
        Board board = checkBoardOwner(boardId, memberId);
        board.update(boardRequestDto);
        return new BoardResponseDto(board);
    }

    @Transactional
    public void deleteBoard(long boardId, Long memberId) {
        Board board = checkBoardOwner(boardId, memberId);
        boardRepository.delete(board);
    }


    /**************
     * 예외처리부분
     * @param boardId
     * @param memberId
     * @return
     */
    public Board checkBoardOwner(long boardId, long memberId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.BOARD_NOT_FOUND));

        if (!board.getMember().getId().equals(memberId)) {
            throw new CustomException(ExceptionErrorCode.UNAUTHORIZED_ACCESS);
        }

        return board;
    }

    public Board checkBoardId(long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ExceptionErrorCode.BOARD_NOT_FOUND));

        return board;
    }

    public void checkBoardContent(BoardRequestDto boardRequestDto) {
        if (boardRequestDto.getTitle().length() > 30) {
            throw new CustomException(ExceptionErrorCode.TITLE_LENGTH_SHORT);
        }

        if (boardRequestDto.getContents() == null) {
            throw new CustomException(ExceptionErrorCode.CONTENT_IS_NULL);
        }
    }

}
