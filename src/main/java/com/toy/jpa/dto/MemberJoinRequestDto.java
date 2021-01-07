package com.toy.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinRequestDto {

    private String name;
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;

}
