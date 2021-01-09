package com.toy.jpa.domain;

import com.toy.jpa.dto.MemberJoinRequestDto;
import com.toy.jpa.dto.UpdateMemberRequestDto;
import com.toy.jpa.exception.MemberExitException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MemberTest {

    @Test
    @DisplayName("회원 가입")
    public void joinMemberTest() {
        //given
        MemberJoinRequestDto dto = getMemberJoinRequestDto();
        Member member = new Member();

        //when
        member.joinMember(dto);

        //then
        assertAll(
                () -> assertNull(member.getId()),
                () -> assertEquals(member.getName(), "test"),
                () -> assertEquals(member.getEmail(), "test"),
                () -> assertEquals(member.getPassword(), "test"),
                () -> assertEquals(member.getAddress().getCity(), "test"),
                () -> assertEquals(member.getAddress().getStreet(), "test"),
                () -> assertEquals(member.getAddress().getZipcode(), "test")
        );
    }

    @Test
    @DisplayName("회원 수정")
    public void changeMemberTest() {
        //given
        UpdateMemberRequestDto dto = getUpdateMemberRequestDto();
        Member member = MemberDummy.memberBuilder();

        //when
        member.changeMember(dto);

        //then
        assertAll(
                () -> assertNotNull(member.getId()),
                () -> assertEquals(member.getName(), "test"),
                () -> assertEquals(member.getPassword(), "test"),
                () -> assertEquals(member.getEmail(), "test"),
                () -> assertEquals(member.getStatus(), ExitStatus.JOIN),
                () -> assertEquals(member.getAddress().getCity(), "test"),
                () -> assertEquals(member.getAddress().getStreet(), "test"),
                () -> assertEquals(member.getAddress().getZipcode(), "test")
        );
    }

    @Test
    @DisplayName("회원 탈퇴")
    public void changeStatusTest() {
        //given
        Member member = MemberDummy.memberBuilder();

        //when
        member.changeStatus();

        //then
        assertEquals(member.getStatus(), ExitStatus.EXIT);
    }

    @Test
    @DisplayName("탈퇴된 회원을 탈퇴시 에러 발생")
    public void changeStatusExceptionTest() {
        Member member = MemberDummy.exitMemberBuilder();

        assertThrows(
                MemberExitException.class,
                () -> member.changeStatus(),
                () -> "탈퇴된 회원은 탈퇴시킬시 예외 발생"
        );
    }

    private MemberJoinRequestDto getMemberJoinRequestDto() {
        return MemberJoinRequestDto.builder()
                .name("test")
                .email("test")
                .password("test")
                .city("test")
                .street("test")
                .zipcode("test")
                .build();
    }

    private UpdateMemberRequestDto getUpdateMemberRequestDto() {
        return UpdateMemberRequestDto.builder()
                .name("test")
                .email("test")
                .password("test")
                .city("test")
                .street("test")
                .zipcode("test")
                .build();
    }
}