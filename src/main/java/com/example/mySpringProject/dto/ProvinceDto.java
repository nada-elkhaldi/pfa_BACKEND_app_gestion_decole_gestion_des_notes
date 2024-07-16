package com.example.mySpringProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor


public class ProvinceDto {
    private int id;
    private String nomProvince;

    public ProvinceDto(int id, String nomProvince) {
        this.id = id;
        this.nomProvince = nomProvince;
    }
}
