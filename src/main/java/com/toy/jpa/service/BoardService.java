package com.toy.jpa.service;

import com.toy.jpa.domain.Board;
import com.toy.jpa.dto.UpdateBoardRequestDto;
import com.toy.jpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void post(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public void update(Long boardId, UpdateBoardRequestDto dto) {
        Optional<Board> board = boardRepository.findById(boardId);
        board.get().changeContent(dto);
    }

    @Transactional
    public void delete(Long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        board.get().deleteBoard();
    }
}
