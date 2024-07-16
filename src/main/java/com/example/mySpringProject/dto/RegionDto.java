package com.example.mySpringProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor


public class RegionDto {
    private int id;
    private String nomRegion;

    public RegionDto(int id, String nomRegion) {
        this.id = id;
        this.nomRegion = nomRegion;
    }
}
