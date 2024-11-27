package com.example.mySpringProject.dto;

import com.example.mySpringProject.model.Region;
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
    private Region region;

    public ProvinceDto(int id, String nomProvince, Region region) {
        this.id = id;
        this.nomProvince = nomProvince;
        this.region = region;
    }
}
