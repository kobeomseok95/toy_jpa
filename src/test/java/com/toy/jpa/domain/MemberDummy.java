package com.toy.jpa.domain;

public class MemberDummy {

    public static Member memberBuilder() {
        return Member.builder()
                .id(1L)
                .name("test")
                .password("test")
                .email("test@email.com")
                .status(ExitStatus.JOIN)
                .address(
                        addressBuilder()
                )
                .build();
    }

    public static Member exitMemberBuilder() {
        return Member.builder()
                .id(1L)
                .name("test")
                .password("test")
                .email("test@email.com")
                .status(ExitStatus.EXIT)
                .address(
                        addressBuilder()
                )
                .build();
    }

    public static Address addressBuilder() {
        return Address.builder()
                .city("test")
                .street("test")
                .zipcode("test")
                .build();
    }
}
