package com.toy.jpa.dto;

import com.toy.jpa.domain.Member;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindMemberDto {

    private Long id;
    private String name;
    private String email;
    private String city;
    private String street;
    private String zipcode;

    public FindMemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.city = member.getAddress().getCity();
        this.street = member.getAddress().getStreet();
        this.zipcode = member.getAddress().getZipcode();
    }
}
