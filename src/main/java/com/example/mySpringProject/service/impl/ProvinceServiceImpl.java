package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.dto.ProvinceDto;
import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.mapper.ProvinceMapper;
import com.example.mySpringProject.model.Province;
import com.example.mySpringProject.model.Region;
import com.example.mySpringProject.repository.ProvinceRepository;
import com.example.mySpringProject.repository.RegionRepository;
import com.example.mySpringProject.service.ProvinceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProvinceServiceImpl implements ProvinceService
{

    private ProvinceRepository  provinceRepository;
    private ProvinceMapper provinceMapper;
    private RegionRepository regionRepository;

    @Override
    public ProvinceDto addProvince(ProvinceDto provinceDto) {
        Province province = ProvinceMapper.mapToProvince(provinceDto);
        Region region = regionRepository.findById(provinceDto.getRegion().getId())
                .orElseThrow(() -> new RuntimeException("Region not found"));

        province.setRegion(region);
        Province savedProvince = provinceRepository.save(province);
        return ProvinceMapper.mapToProvinceDto(savedProvince);
    }

    @Override
    public List<ProvinceDto> getAllProvinces() {
            List<Province> provinces = provinceRepository.findAll();
        return provinces.stream().map((province)-> provinceMapper.mapToProvinceDto(province))
                .collect(Collectors.toList());
    }

    @Override
    public Province getProvinceById(Integer id) {
        return provinceRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User with id " + id + " not found"));

    }

    @Override
    public Province updateProvince(Integer id, Province request) {
        Province province = provinceRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Province with id " + id + " not found")
        );
        province.setNomProvince(request.getNomProvince());

        Province savedProvince = provinceRepository.save(province);
        return savedProvince;
    }

    @Override
    public void deleteProvince(Integer id) {
        Province province = provinceRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Province with id " + id + " not found")
        );
        provinceRepository.deleteById(id);

    }
}
