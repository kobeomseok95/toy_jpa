package com.toy.jpa.domain;

import com.toy.jpa.domain.base.BaseEntity;
import com.toy.jpa.domain.status.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    public void changeComment(String changeComment) {
        this.comment = changeComment;
    }

    public void deleteComment() {
        if (this.status != CommentStatus.COMMENTED) {
            throw new IllegalStateException("이미 삭제된 댓글입니다.");
        }
        this.status = CommentStatus.DELETE;
    }

    public void addMemberAndBoard(Member member, Board board) {
        this.member = member;
        member.getComments().add(this);

        this.board = board;
        board.getComments().add(this);
    }
}
