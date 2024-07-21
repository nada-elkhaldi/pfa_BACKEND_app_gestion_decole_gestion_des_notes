package com.example.mySpringProject.controller;

import com.example.mySpringProject.dto.ProvinceDto;
import com.example.mySpringProject.model.Province;
import com.example.mySpringProject.service.ProvinceService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api3",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})

public class ProvinceController {

    private final ProvinceService provinceService;

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/addProvince")
    public ResponseEntity<ProvinceDto> addRegion(@RequestBody ProvinceDto provinceDto) {
        ProvinceDto savedProvince= provinceService.addProvince(provinceDto);
        return ResponseEntity.ok(savedProvince);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/provinces")
    public ResponseEntity<List<ProvinceDto>> getAllProvinces() {
        List<ProvinceDto> provinces = provinceService.getAllProvinces();
        return  ResponseEntity.ok(provinces);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PutMapping("/updateProvince/{id}")
    public ResponseEntity<Province> updateProvince(@PathVariable("id") Integer id, @RequestBody Province updatedProvince) {
        Province province= provinceService.updateProvince(id, updatedProvince);
        return ResponseEntity.ok(province);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @DeleteMapping("/deleteProvince/{id}")
    public ResponseEntity<String> deleteProvince(@PathVariable("id") Integer id) {
        provinceService.deleteProvince(id);
        return ResponseEntity.ok("Province deleted");

    }
}
