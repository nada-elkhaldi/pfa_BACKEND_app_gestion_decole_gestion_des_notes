package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.dto.RegionDto;
import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.mapper.RegionMapper;
import com.example.mySpringProject.model.Region;
import com.example.mySpringProject.repository.RegionRepository;
import com.example.mySpringProject.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegionServiceImpl implements RegionService
{

    private RegionRepository regionRepository;
    private RegionMapper regionMapper;
    @Override
    public RegionDto addRegion(RegionDto regionDto) {
        Region region = RegionMapper.mapToRegion(regionDto);
        Region savedRegion = regionRepository.save(region);
        return RegionMapper.mapToRegionDto(savedRegion);
    }

    @Override
    public List<RegionDto> getAllRegions() {
        List<Region> regions = regionRepository.findAll();
        return regions.stream().map((region)-> regionMapper.mapToRegionDto(region))
                .collect(Collectors.toList());
    }


    @Override
    public Region getRegionById(Integer id) {
        return regionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User with id " + id + " not found"));

    }
    @Override
    public Region updateRegion(Integer id, Region request){
        Region region = regionRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Region with id " + id + " not found")
        );
        region.setNomRegion(request.getNomRegion());

        Region savedRegion = regionRepository.save(region);
        return savedRegion;

    }
    @Override
    public void deleteRegion(Integer id) {
        Region region = regionRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Region with id " + id + " not found")
        );
        regionRepository.deleteById(id);
    }
}
