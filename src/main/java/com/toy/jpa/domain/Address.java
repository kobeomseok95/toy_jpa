package com.toy.jpa.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
