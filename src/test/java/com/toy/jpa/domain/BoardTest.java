package com.toy.jpa.domain;

import com.toy.jpa.domain.status.BoardStatus;
import com.toy.jpa.dto.UpdateBoardRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    @DisplayName("게시글 등록")
    void createBoardTest() {
        Board board = BoardDummy.BoardBuilder();

        assertAll(
                () -> assertEquals(board.getMember().getId(), 1L),
                () -> assertEquals(board.getMember().getBoards().size(), 1),
                () -> assertEquals(board.getMember().getBoards().get(0).getId(), 1L)
        );
    }

    @Test
    @DisplayName("게시글 수정")
    void updateBoardTest() {
        //given
        Board board = BoardDummy.BoardBuilder();
        UpdateBoardRequestDto dto = getUpdateBoardRequestDto();

        //when
        board.changeContent(dto);

        //then
        assertAll(
                () -> assertEquals(board.getTitle(), "update"),
                () -> assertEquals(board.getContent(), "update"),
                () -> assertEquals(board.getMember().getBoards().get(0).getTitle(), "update"),
                () -> assertEquals(board.getMember().getBoards().get(0).getContent(), "update")
        );

    }

    @Test
    @DisplayName("게시글 조회수 증가")
    void viewCountTest() {
        Board board = BoardDummy.BoardBuilder();

        board.updateViews();

        assertEquals(board.getViews(), 1);
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteBoard() {
        Board board = BoardDummy.BoardBuilder();

        board.deleteBoard();

        assertEquals(board.getStatus(), BoardStatus.DELETE);
    }

    @Test
    @DisplayName("게시글 삭제 예외 확인")
    void deleteBoardException() {
        Board board = BoardDummy.DeleteBoardBuilder();

        assertThrows(
                IllegalStateException.class,
                board::deleteBoard,
                () -> "삭제된 게시글은 삭제할 수 없습니다."
        );
    }

    private UpdateBoardRequestDto getUpdateBoardRequestDto() {

        return UpdateBoardRequestDto.builder()
                .title("update")
                .content("update")
                .build();
    }
}