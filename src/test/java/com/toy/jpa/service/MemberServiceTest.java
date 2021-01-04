package com.toy.jpa.service;

import com.toy.jpa.dto.FindMemberDto;
import com.toy.jpa.dto.UpdateMemberRequestDto;
import com.toy.jpa.exception.MemberExitException;
import com.toy.jpa.domain.Address;
import com.toy.jpa.domain.ExitStatus;
import com.toy.jpa.domain.Member;
import com.toy.jpa.repository.MemberRepository;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

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

        FindMemberDto findMember = memberService.findById(member.getId());
        assertAll(
                () -> assertEquals(findMember.getName(), "고범석", () -> "이름이 같지 않다."),
                () -> assertEquals(findMember.getEmail(), "kobumssh@naver.com", () -> "메일이 같지 않다."),
                () -> assertEquals(findMember.getCity(), "서울시", () -> "city가 같지 않다."),
                () -> assertEquals(findMember.getStreet(), "광진구 중곡동", () -> "street이 같지 않다."),
                () -> assertEquals(findMember.getZipcode(), "1923", () -> "zipcode가 같지 않다.")
        );
    }

    @Test
    @DisplayName("이메일로 회원 찾기")
    void findByEmailTest() {
        String email = "kobumssh10@naver.com";

        FindMemberDto findMember = memberService.findByEmail(email);
        assertAll(
                () -> assertEquals(findMember.getName(), "고범석10", () -> "이름이 같지 않다."),
                () -> assertEquals(findMember.getEmail(), "kobumssh10@naver.com", () -> "메일이 같지 않다."),
                () -> assertEquals(findMember.getCity(), "서울시", () -> "city가 같지 않다."),
                () -> assertEquals(findMember.getStreet(), "광진구 중곡동", () -> "street이 같지 않다."),
                () -> assertEquals(findMember.getZipcode(), "1923", () -> "zipcode가 같지 않다.")
        );
    }

    @Test
    @DisplayName("회원 수정")
    void updateMemberTest() {
        String changeName = "바뀜";
        String changeEmail = "XXX.com";
        String changePassword = "1234";
        String changeCity = "changeCity";
        String changeStreet = "changeStreet";
        String changeZipcode = "changeZipcode";

        Optional<Member> member = memberRepository.findByEmail("kobumssh55@naver.com");
        memberService.updateMember(member.get().getId(), UpdateMemberRequestDto.builder()
                        .name(changeName)
                        .email(changeEmail)
                        .password(changePassword)
                        .city(changeCity)
                        .street(changeStreet)
                        .zipcode(changeZipcode)
                        .build()
        );
        em.flush();
        em.clear();


        Optional<Member> changedMember = memberRepository.findByEmail(changeEmail);
        assertAll(
                () -> assertEquals(changedMember.get().getId(), member.get().getId(), () -> "id가 다릅니다."),
                () -> assertEquals(changedMember.get().getName(), changeName, () -> "이름이 다릅니다."),
                () -> assertEquals(changedMember.get().getEmail(), changeEmail, () -> "메일이 다릅니다."),
                () -> assertEquals(changedMember.get().getPassword(), changePassword, () -> "비밀번호가 다릅니다."),
                () -> assertEquals(changedMember.get().getAddress().getCity(), changeCity, () -> "city가 다릅니다."),
                () -> assertEquals(changedMember.get().getAddress().getStreet(), changeStreet, () -> "street이 다릅니다."),
                () -> assertEquals(changedMember.get().getAddress().getZipcode(), changeZipcode, () -> "zipcode가 다릅니다.")
        );
    }

    @Test
    @DisplayName("활동중인 회원 탈퇴 및 탈퇴된 회원은 조회 금지")
    void memberExitTest() throws Exception {
        String email = "kobumssh10@naver.com";

        Optional<Member> member = memberRepository.findByEmail(email);
        member.get().changeStatus();

        assertEquals(member.get().getStatus(), ExitStatus.EXIT);

        em.flush();
        em.clear();

        Optional<Member> exitMember = memberRepository.findByEmail(email);
        assertThrows(
                NoSuchElementException.class,
                () -> exitMember.get(),
                () -> "조회되지 않아야 합니다."
        );
    }
}













