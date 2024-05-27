package com.example.mySpringProject.controller;

import com.example.mySpringProject.dto.MatiereDto;
import com.example.mySpringProject.service.MatiereService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api2",  method = {RequestMethod.POST ,RequestMethod.GET,  RequestMethod.OPTIONS})
public class MatiereController {
    private final MatiereService matiereService;


    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/addMatieres")
    public ResponseEntity<List<MatiereDto>> addMatiere(@RequestBody List<MatiereDto> matiereDto) {
        List<MatiereDto> savedMatieres = matiereService.createMatiere(matiereDto);
        return ResponseEntity.ok(savedMatieres);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/matieres")
    public ResponseEntity<List<MatiereDto>> getAllMatieres() {
        List<MatiereDto> matieres = matiereService.getAllMatieres();
        return ResponseEntity.ok(matieres);
    }
}
