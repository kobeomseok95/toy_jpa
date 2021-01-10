package com.toy.jpa.service;

import com.toy.jpa.domain.Address;
import com.toy.jpa.dto.FindMemberResponseDto;
import com.toy.jpa.dto.MemberJoinRequestDto;
import com.toy.jpa.domain.status.ExitStatus;
import com.toy.jpa.domain.Member;
import com.toy.jpa.dto.UpdateMemberRequestDto;
import com.toy.jpa.exception.MemberExitException;
import com.toy.jpa.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입")
    void joinTest() {
        MemberJoinRequestDto dto = createMemberDto();
        given(memberRepository.save(any(Member.class)))
                .willReturn(createMember(dto));

        Member save = memberRepository.save(createMember(dto));
        assertNotNull(save.getCreatedDate());
        assertNotNull(save.getLastModifiedDate());
        assertEquals(save.getStatus(), ExitStatus.JOIN);

        memberService.join(dto);

        verify(memberRepository, atLeastOnce()).save(any(Member.class));
    }

    @Test
    @DisplayName("이메일로 회원 찾기")
    void findByEmailTest() {
        String email = "test@email.com";
        Member member = createMemberByEmail(email);
        FindMemberResponseDto findDto = createFindMemberResponseDto(member);

        given(memberRepository.findByEmailAndStatus(email, ExitStatus.JOIN))
                .willReturn(Optional.of(member));

        FindMemberResponseDto findByEmailMemberDto = memberService.findByEmail(email);

        then(memberRepository).should().findByEmailAndStatus(email, ExitStatus.JOIN);
        assertAll(
                () -> assertNotNull(findByEmailMemberDto),
                () -> assertEquals(email, findByEmailMemberDto.getEmail())
        );
    }

    @Test
    @DisplayName("id로 회원 찾기")
    void findByIdSueecssTest() {
        Member member = createMember(createMemberDto());
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));

        Member findMember = memberService.findById(111L);

        assertAll(
                () -> assertNotNull(findMember),
                () -> assertEquals(findMember.getId(), 1L),
                () -> assertEquals(findMember.getName(), "test")
        );
    }

    @Test
    @DisplayName("id로 회원 찾기 실패")
    void findByIdFailTest() {
        assertThrows(
                NoSuchElementException.class,
                () -> memberService.findById(123L),
                () -> "return Type:Optional, NoSuchElementException이 발생해야 한다."
        );
    }

    @Test
    @DisplayName("회원 수정")
    void updateMemberTest() {
        UpdateMemberRequestDto dto = createUpdateMemberRequestDto();
        Member updateMember = createMember();
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(updateMember));

        memberService.updateMember(1L, dto);

        then(memberRepository).should().findById(anyLong());
        assertAll(
                () -> assertEquals(updateMember.getName(), "update"),
                () -> assertEquals(updateMember.getEmail(), "update"),
                () -> assertEquals(updateMember.getPassword(), "update"),
                () -> assertEquals(updateMember.getAddress().getCity(), "update"),
                () -> assertEquals(updateMember.getAddress().getStreet(), "update"),
                () -> assertEquals(updateMember.getAddress().getZipcode(), "update"),
                () -> assertEquals(updateMember.getStatus(), ExitStatus.JOIN)
        );
    }

    @Test
    @DisplayName("회원 탈퇴")
    void memberExitTest() throws MemberExitException {
        Member exitMember = createMember();
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(exitMember));

        memberService.exitMember(1L);

        assertEquals(exitMember.getStatus(), ExitStatus.EXIT);
    }

    @Test
    @DisplayName("회원 탈퇴 실패")
    void memberExitTestFail() {
        Member exitMember = createExitMember();
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(exitMember));

        assertThrows(
                MemberExitException.class,
                () -> memberService.exitMember(1L)
        );

    }

    private Member createExitMember() {
        return Member.builder()
                .id(1L)
                .name("test")
                .email("test@email.com")
                .password("test")
                .address(
                        Address.builder()
                                .city("test")
                                .street("test")
                                .zipcode("test")
                                .build()
                )
                .status(ExitStatus.EXIT)
                .build();
    }

    private UpdateMemberRequestDto createUpdateMemberRequestDto() {
        return UpdateMemberRequestDto.builder()
                .name("update")
                .email("update")
                .password("update")
                .city("update")
                .street("update")
                .zipcode("update")
                .build();
    }

    private Member createMember() {
        return Member.builder()
                .id(1L)
                .name("test")
                .email("test@email.com")
                .password("test")
                .address(
                        Address.builder()
                                .city("test")
                                .street("test")
                                .zipcode("test")
                                .build()
                )
                .status(ExitStatus.JOIN)
                .build();
    }

    private FindMemberResponseDto createFindMemberResponseDto(Member member) {
        return FindMemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .city(member.getAddress().getCity())
                .street(member.getAddress().getStreet())
                .zipcode(member.getAddress().getZipcode())
                .build();
    }

    private Member createMemberByEmail(String email) {
        MemberJoinRequestDto dto = createMemberDto();
        dto.setEmail(email);
        return createMember(dto);
    }

    private Member createMember(MemberJoinRequestDto dto) {
        Member member = new Member();
        member.joinMember(dto);

        ReflectionTestUtils.setField(member, "id", 1L);
        ReflectionTestUtils.setField(member, "createdDate", LocalDateTime.now());
        ReflectionTestUtils.setField(member, "lastModifiedDate", LocalDateTime.now());
        ReflectionTestUtils.setField(member, "status", ExitStatus.JOIN);

        return member;
    }

    private MemberJoinRequestDto createMemberDto() {
        return MemberJoinRequestDto.builder()
                .name("test")
                .email("test@email.com")
                .password("test")
                .city("test")
                .street("test")
                .zipcode("test")
                .build();
    }

}













