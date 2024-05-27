package com.example.mySpringProject.controller;


import com.example.mySpringProject.dto.ClasseDto;
import com.example.mySpringProject.service.ClasseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor


@RequestMapping(value = "/api", method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
@RestController
public class ClasseController {
    private final ClasseService classeService;

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/addClasse")
    public ResponseEntity<List<ClasseDto>> addClasse(@RequestBody List<ClasseDto> classeDto) {
        List<ClasseDto> savedClasses = classeService.createClasse(classeDto);
        return ResponseEntity.ok(savedClasses);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/classes")
    public ResponseEntity<List<ClasseDto>> getAllClasses() {
        List<ClasseDto> classes = classeService.getAllClasses();
        return ResponseEntity.ok(classes);
    }



}
