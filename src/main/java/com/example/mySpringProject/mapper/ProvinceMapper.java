package com.example.mySpringProject.mapper;


import com.example.mySpringProject.dto.ProvinceDto;
import com.example.mySpringProject.model.Province;
import org.springframework.stereotype.Component;


@Component
public class ProvinceMapper {

    public static ProvinceDto mapToProvinceDto(Province province) {
        return new ProvinceDto(
                province.getId(),
                province.getNomProvince()
        );

    }

    public static Province mapToProvince(ProvinceDto provinceDto) {
        return new Province(
                provinceDto.getId(),
                provinceDto.getNomProvince()
        );
    }

}
