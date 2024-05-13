package com.example.mySpringProject.controller;


import com.example.mySpringProject.dto.SemestreDto;
import com.example.mySpringProject.service.SemestreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "api/v1/semestres")
public class SemestreController {

    private final SemestreService semestreService;

    @PostMapping
    public ResponseEntity<SemestreDto> addSemestre(@RequestBody SemestreDto semestreDto) {
        SemestreDto savedSemestre= semestreService.createSemestre(semestreDto);
        return ResponseEntity.ok(savedSemestre);
    }

    @GetMapping
    public ResponseEntity<List<SemestreDto>> getAllSemestres() {
        List<SemestreDto> semestres = semestreService.getAllSemestre();
        return  ResponseEntity.ok(semestres);
    }
}
