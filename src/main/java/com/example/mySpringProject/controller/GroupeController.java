package com.example.mySpringProject.controller;


import com.example.mySpringProject.dto.GroupeDto;
import com.example.mySpringProject.model.Groupe;
import com.example.mySpringProject.service.GroupeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "api/v1/groupes")
public class GroupeController {

    private final GroupeService groupeService;

    @PostMapping
    public ResponseEntity<GroupeDto> addGroupe(@RequestBody GroupeDto groupeDto) {
        GroupeDto savedGroup= groupeService.createGroupe(groupeDto);
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GroupeDto>> getAllGroupes() {
         List<GroupeDto> groupes = groupeService.getAllGroupes();
         return  ResponseEntity.ok(groupes);
    }
}
