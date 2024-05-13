package com.example.mySpringProject.controller;


import com.example.mySpringProject.dto.ClasseDto;
import com.example.mySpringProject.service.ClasseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "api/v1/classes")
public class ClasseController {
    private final ClasseService classeService;

    @PostMapping
    public ResponseEntity<List<ClasseDto>> addClasse(@RequestBody List<ClasseDto> classeDto) {
        List<ClasseDto> savedClasses = classeService.createClasse(classeDto);
        return ResponseEntity.ok(savedClasses);
    }

    @GetMapping
    public ResponseEntity<List<ClasseDto>> getAllClasses() {
        List<ClasseDto> classes = classeService.getAllClasses();
        return ResponseEntity.ok(classes);
    }
}
