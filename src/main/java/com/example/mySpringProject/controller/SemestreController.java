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
@RequestMapping(value= "/api4",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
public class SemestreController {

    private final SemestreService semestreService;

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/addSemestre")
    public ResponseEntity<SemestreDto> addSemestre(@RequestBody SemestreDto semestreDto) {
        SemestreDto savedSemestre= semestreService.createSemestre(semestreDto);
        return ResponseEntity.ok(savedSemestre);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/semestres")
    public ResponseEntity<List<SemestreDto>> getAllSemestres() {
        List<SemestreDto> semestres = semestreService.getAllSemestre();
        return  ResponseEntity.ok(semestres);
    }
}
