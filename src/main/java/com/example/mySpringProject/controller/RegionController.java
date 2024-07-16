package com.example.mySpringProject.controller;


import com.example.mySpringProject.dto.RegionDto;
import com.example.mySpringProject.model.Region;
import com.example.mySpringProject.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api2",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})

public class RegionController {

    private final RegionService regionService;
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/addRegion")
    public ResponseEntity<RegionDto> addRegion(@RequestBody RegionDto regionDto) {
        RegionDto savedRegion= regionService.addRegion(regionDto);
        return ResponseEntity.ok(savedRegion);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/regions")
    public ResponseEntity<List<RegionDto>> getAllRegions() {
        List<RegionDto> regions = regionService.getAllRegions();
        return  ResponseEntity.ok(regions);
    }

    @PutMapping("/updateRegion/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable("id") Integer id, @RequestBody Region updatedRegion) {
        Region region= regionService.updateRegion(id, updatedRegion);
        return ResponseEntity.ok(region);
    }

    @DeleteMapping("/deleteRegion/{id}")
    public ResponseEntity<String> deleteRegion(@PathVariable("id") Integer id) {
        regionService.deleteRegion(id);
        return ResponseEntity.ok("Region deleted");

    }
}
