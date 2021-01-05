package com.toy.jpa.repository;

import com.toy.jpa.domain.Address;
import com.toy.jpa.domain.ExitStatus;
import com.toy.jpa.domain.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    //실제 DB에 테스트
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TestEntityManager em;


    private void init() {
        for (int i = 0; i < 50; i++) {
            memberRepository.save(Member.builder()
                    .name("고범석" + i)
                    .email("kobumssh" + i + "@naver.com")
                    .password("1234")
                    .address(
                            Address.builder()
                                    .city("서울시")
                                    .street("광진구")
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
                .name("고읍석")
                .email("koko@naver.com")
                .password("1234")
                .address(
                        Address.builder()
                                .city("서울시")
                                .street("광진구")
                                .zipcode("1923")
                                .build()
                )
                .status(ExitStatus.JOIN)
                .build();
        Long saveId = memberRepository.save(member).getId();

        assertEquals(saveId, member.getId());
    }

    @Test
    @DisplayName("메일로 회원 찾기")
    void findMemberEmailTest() {
        init();

        String email = "kobumssh10@naver.com";
        Optional<Member> findMember = memberRepository.findByEmailAndStatus(email, ExitStatus.JOIN);
        assertEquals(findMember.get().getEmail(), email);
    }

    @Test
    @DisplayName("등록되지 않은 메일로 회원을 찾으면 empty")
    void findMemberEmailTestNone() {
        init();

        String email = "kobumssh101@naver.com";
        assertEquals(Optional.empty(), memberRepository.findByEmailAndStatus(email, ExitStatus.JOIN));
    }

    @Test
    @DisplayName("이름으로 회원 찾기")
    void findMemberNameTest() {
        init();

        String name = "고범석10";
        Optional<Member> findMember = memberRepository.findByNameAndStatus(name, ExitStatus.JOIN);
        assertAll(
                () -> assertNotEquals(Optional.empty(), findMember),
                () -> assertEquals(findMember.get().getName(), name)
        );
    }

    @Test
    @DisplayName("이름으로 회원 찾기 실패")
    void findMemberNameTestNone() {
        init();

        String name = "고범석1000";
        assertEquals(Optional.empty(), memberRepository.findByNameAndStatus(name, ExitStatus.JOIN));
    }
}















