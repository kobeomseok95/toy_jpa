package com.toy.jpa.domain;

import com.toy.jpa.MemberExitException;
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

    public void changeMember(Member changeMember) {
        this.name = changeMember.getName();
        this.email = changeMember.getEmail();
        this.password = changeMember.getPassword();
        this.address = changeMember.getAddress();
        this.status = changeMember.getStatus();
    }
}
