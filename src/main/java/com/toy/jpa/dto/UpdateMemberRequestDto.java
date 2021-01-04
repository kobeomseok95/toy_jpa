package com.toy.jpa.dto;

import com.toy.jpa.domain.ExitStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMemberRequestDto {
    private String name;
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;
}
