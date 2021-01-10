package com.toy.jpa.domain;

import com.toy.jpa.domain.base.BaseEntity;
import com.toy.jpa.domain.status.BoardStatus;
import com.toy.jpa.dto.UpdateBoardRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString(of = {"title", "content", "views", "status", "comments"})
public class Board extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String content;
    private int views;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    public void updateViews() {
        views++;
    }

    public void addMember(Member member) {
        this.member = member;
        member.getBoards().add(this);
    }

    public void changeContent(UpdateBoardRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void deleteBoard() {
        if (this.status != BoardStatus.POSTED) {
            throw new IllegalStateException("이미 삭제된 게시글입니다.");
        }
        this.status = BoardStatus.DELETE;
    }
}
