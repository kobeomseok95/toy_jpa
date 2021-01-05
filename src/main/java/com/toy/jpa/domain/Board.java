package com.toy.jpa.domain;

import com.toy.jpa.domain.base.BaseEntity;
import com.toy.jpa.dto.UpdateBoardRequestDto;
import lombok.*;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
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

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    public void changeContent(UpdateBoardRequestDto dto) {
        this.title = title;
        this.content = content;
    }

    public void changeStatus() {
        if (this.status != BoardStatus.POST) {
            throw new IllegalStateException("이미 삭제된 게시글입니다.");
        }
        this.status = BoardStatus.DELETE;
    }
}
