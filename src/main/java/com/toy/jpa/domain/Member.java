package com.toy.jpa.domain;

import com.toy.jpa.dto.UpdateMemberRequestDto;
import com.toy.jpa.exception.MemberExitException;
import com.toy.jpa.domain.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString(of = {"name", "email", "password", "address", "status"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "memberId")
    private Long id;

    private String name;
    private String email;
    private String password;

    @Embedded
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private ExitStatus status;

    public void changeStatus() throws MemberExitException {
        if (status.equals(ExitStatus.JOIN)) {
            status = ExitStatus.EXIT;
        } else {
            throw new MemberExitException("이미 탈퇴된 회원입니다.");
        }
    }

    public void changeMember(UpdateMemberRequestDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.address = Address.builder()
                .city(dto.getCity())
                .street(dto.getStreet())
                .zipcode(dto.getZipcode())
                .build();
    }
}
