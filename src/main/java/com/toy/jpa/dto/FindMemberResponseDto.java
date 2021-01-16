package com.toy.jpa.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindMemberResponseDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;
    private List<String> roles = new ArrayList<>();

}
