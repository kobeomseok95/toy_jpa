package com.toy.jpa.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindMemberResponseDto {

    private Long id;
    private String name;
    private String email;
    private String city;
    private String street;
    private String zipcode;

}
