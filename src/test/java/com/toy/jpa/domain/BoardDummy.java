package com.toy.jpa.domain;

import com.toy.jpa.domain.status.BoardStatus;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardDummy {

    public static Board BoardBuilder() {
        Member member = MemberDummy.memberBuilder();
        Board board = Board.builder()
                .id(1L)
                .title("test")
                .content("test")
                .views(0)
                .status(BoardStatus.POSTED)
                .comments(new ArrayList<>())
                .build();

        board.addMember(member);

        return board;
    }

    public static Board DeleteBoardBuilder() {
        Member member = MemberDummy.memberBuilder();
        Board board = Board.builder()
                .id(1L)
                .title("test")
                .content("test")
                .views(0)
                .status(BoardStatus.DELETE)
                .comments(new ArrayList<>())
                .build();

        board.addMember(member);

        return board;
    }
}
