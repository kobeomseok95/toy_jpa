package com.toy.jpa.domain;

import com.toy.jpa.domain.status.CommentStatus;

public class CommentDummy {

    public static Comment commentBuilder() {
        Comment comment = Comment.builder()
                .id(1L)
                .comment("test")
                .status(CommentStatus.COMMENTED)
                .build();

        Board board = BoardDummy.BoardBuilder();
        Member member = MemberDummy.memberBuilder();

        comment.addMemberAndBoard(member, board);

        return comment;
    }

    public static Comment deleteCommentBuilder() {
        Comment comment = Comment.builder()
                .id(1L)
                .comment("test")
                .status(CommentStatus.DELETE)
                .build();

        Board board = BoardDummy.BoardBuilder();
        Member member = MemberDummy.memberBuilder();

        comment.addMemberAndBoard(member, board);

        return comment;
    }
}
