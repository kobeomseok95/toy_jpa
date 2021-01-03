package com.toy.jpa.service;

import com.toy.jpa.MemberExitException;
import com.toy.jpa.domain.Address;
import com.toy.jpa.domain.ExitStatus;
import com.toy.jpa.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.reflections.util.ConfigurationBuilder.build;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setMembers() {
        for (int i = 0; i < 100; i++) {
            memberService.join(Member.builder()
                    .name("고범석" + i)
                    .email("kobumssh" + i + "@naver.com")
                    .password("1234")
                    .address(
                            Address.builder()
                                    .city("서울시")
                                    .street("광진구 중곡동")
                                    .zipcode("1923")
                                    .build()
                    )
                    .status(ExitStatus.JOIN)
                    .build());
        }
    }

    @Test
    @DisplayName("회원가입")
    void joinTest() {
        Member member = Member.builder()
                .name("고범석")
                .email("kobumssh@naver.com")
                .password("1234")
                .address(
                        Address.builder()
                                .city("서울시")
                                .street("광진구 중곡동")
                                .zipcode("1923")
                                .build()
                )
                .status(ExitStatus.JOIN)
                .build();
        memberService.join(member);

        Optional<Member> findMember = memberService.findById(member.getId());
        assertThat(findMember.get()).isEqualTo(member);
    }

    @Test
    @DisplayName("이메일로 회원 찾기")
    void findByEmailTest() {
        String email = "kobumssh10@naver.com";

        Optional<Member> findByEmailMember = memberService.findByEmail(email);
        assertThat(findByEmailMember.get())
                .extracting("name")
                .isEqualTo("고범석10");

    }

    @Test
    @DisplayName("회원 수정")
    void updateMemberTest() {   //값 타입 공유참조파트
        Optional<Member> findMember = memberService.findByEmail("kobumssh55@naver.com");
        findMember.get().changeMember(
                Member.builder()
                        .name("바뀜")
                        .email("XXX.com")
                        .password("1234")
                        .address(
                                Address.builder()
                                        .city("asdf")
                                        .street("asdf")
                                        .zipcode("asdf")
                                        .build()
                        )
                        .status(ExitStatus.JOIN)
                        .build()
        );
        em.flush();
        em.clear();

        Optional<Member> changedMember = memberService.findByEmail("XXX.com");
        assertThat(changedMember.get())
                .isEqualTo(findMember.get());
    }

    @Test
    @DisplayName("활동중인 회원 탈퇴")
    void memberExitTest() throws Exception {
        Optional<Member> member = memberService.findByEmail("kobumssh10@naver.com");
        member.get().changeStatus();

        em.flush();
        em.clear();

        Optional<Member> exitMember = memberService.findByEmail("kobumssh10@naver.com");
        assertThat(exitMember.get().getStatus())
                .isEqualTo(ExitStatus.EXIT);
    }

    @Test
    @DisplayName("이미 탈퇴한 회원 에러")
    void memberExitExceptionTest() throws Exception {
        Optional<Member> member = memberService.findByEmail("kobumssh10@naver.com");
        member.get().changeStatus();

        em.flush();
        em.clear();

        Optional<Member> exitMember = memberService.findByEmail("kobumssh10@naver.com");

        assertThrows(MemberExitException.class, () -> {
            exitMember.get().changeStatus();
        });
    }
}












