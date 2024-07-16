package com.example.mySpringProject.mapper;


import com.example.mySpringProject.dto.RegionDto;
import com.example.mySpringProject.model.Region;
import org.springframework.stereotype.Component;


@Component
public class RegionMapper {

    public static RegionDto mapToRegionDto(Region region) {
        return new RegionDto(
                region.getId(),
                region.getNomRegion()
        );

    }

    public static Region mapToRegion(RegionDto regionDto) {
        return new Region(
                regionDto.getId(),
                regionDto.getNomRegion()
        );
    }

}
