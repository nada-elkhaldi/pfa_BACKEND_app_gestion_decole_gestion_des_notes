package com.example.mySpringProject.service;

import com.example.mySpringProject.dto.RegionDto;
import com.example.mySpringProject.model.Region;

import java.util.List;

public interface RegionService {
    RegionDto addRegion(RegionDto regionDto);
     List<RegionDto> getAllRegions();
     Region getRegionById(Integer id);
     Region updateRegion(Integer id, Region region);
     void deleteRegion(Integer id);
}
