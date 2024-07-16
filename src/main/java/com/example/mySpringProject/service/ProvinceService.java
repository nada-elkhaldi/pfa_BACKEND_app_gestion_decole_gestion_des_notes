package com.example.mySpringProject.service;

import com.example.mySpringProject.dto.ProvinceDto;
import com.example.mySpringProject.model.Province;

import java.util.List;

public interface ProvinceService {

    ProvinceDto addProvince(ProvinceDto provinceDto);
    List<ProvinceDto> getAllProvinces();
    Province getProvinceById(Integer id);
    Province updateProvince(Integer id, Province province);
    void deleteProvince(Integer id);
}
