package com.toy.jpa.service;

import com.toy.jpa.domain.Board;
import com.toy.jpa.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberService memberService;

    @BeforeEach
    void setMemberAndBoard() {
        
    }

    @Test
    @DisplayName("게시글 생성")
    void createBoard() {

    }
}


















