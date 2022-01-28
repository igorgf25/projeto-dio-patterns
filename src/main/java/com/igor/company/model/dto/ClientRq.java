package com.igor.company.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRq {

    private String name;

    private String address;

    private String RG;

    private String email;

    private Float debt;
}

