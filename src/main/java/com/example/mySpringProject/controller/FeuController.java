package com.example.mySpringProject.controller;


import com.example.mySpringProject.dto.FeuDto;

import com.example.mySpringProject.model.Feu;

import com.example.mySpringProject.service.FeuService;
import com.example.mySpringProject.service.TauxDisponibiliteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api4",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
public class FeuController {

    private final FeuService feuService;
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/addFeu")
    public ResponseEntity<FeuDto> addFeu(@RequestBody FeuDto feuDto) {
        FeuDto savedFeu= feuService.addFeu(feuDto);
        return ResponseEntity.ok(savedFeu);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/feux")
    public ResponseEntity<List<FeuDto>> getAllFeux() {
        List<FeuDto> feux = feuService.getAllFeux();
        return  ResponseEntity.ok(feux);
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PutMapping("/updateFeu/{id}")
    public ResponseEntity<Feu> updateFeu(@PathVariable("id") Integer id, @RequestBody Feu updatedFeu) {
        Feu feu= feuService.updateFeu(id, updatedFeu);
        return ResponseEntity.ok(feu);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @DeleteMapping("/deleteFeu/{id}")
    public ResponseEntity<String> deleteFeu(@PathVariable("id") Integer id) {
        feuService.deleteFeu(id);
        return ResponseEntity.ok("Feu deleted");

    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/feux/by-region-and-province")
    public List<FeuDto> getFeuxByProvince(

            @RequestParam Integer idProvince) {
        return feuService.getFeuxByProvince( idProvince);
    }

}
