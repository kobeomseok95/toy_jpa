package com.toy.jpa.domain;

import com.toy.jpa.domain.status.CommentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    @Test
    @DisplayName("댓글 생성")
    void createComment() {
        Comment comment = CommentDummy.commentBuilder();

        assertAll(
                () -> assertTrue(comment.getBoard().getComments().contains(comment)),
                () -> assertTrue(comment.getMember().getComments().contains(comment)),
                () -> assertEquals(comment, comment.getMember().getComments().get(0)),
                () -> assertEquals(comment, comment.getBoard().getComments().get(0))
        );
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment(){
        Comment comment = CommentDummy.commentBuilder();

        comment.deleteComment();

        assertEquals(comment.getStatus(), CommentStatus.DELETE);
    }

    @Test
    @DisplayName("댓글 삭제 예외 발생")
    void deleteCommentFail() {
        Comment comment = CommentDummy.deleteCommentBuilder();

        assertThrows(
                IllegalStateException.class,
                comment::deleteComment,
                () -> "삭제된 댓글입니다."
        );
    }

    @Test
    @DisplayName("댓글 수정 하기")
    void updateCommentTest() {
        String updateComment = "update";
        Comment comment = CommentDummy.commentBuilder();

        comment.changeComment(updateComment);

        assertEquals(comment.getComment(), updateComment);
    }
}