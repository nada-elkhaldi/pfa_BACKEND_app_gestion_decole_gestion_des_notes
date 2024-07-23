package com.example.mySpringProject.controller;


import com.example.mySpringProject.model.Declaration;
import com.example.mySpringProject.service.DeclarationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api2",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
public class DeclarationController {
    private final DeclarationService declarationService;
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/addDeclaration")
    public ResponseEntity<Declaration> addDeclaration(@RequestBody Declaration declaration) {
        Declaration saveddeclaration= declarationService.addDeclaration(declaration);
        return ResponseEntity.ok(saveddeclaration);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/declarations")
    public ResponseEntity<List<Declaration>> getAllDeclarations() {
        List<Declaration> declarations = declarationService.getAlldeclarations();
        return  ResponseEntity.ok(declarations);
    }


    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @DeleteMapping("/deleteDeclaration/{id}")
    public ResponseEntity<String> deleteDeclaration(@PathVariable("id") Integer id) {
        declarationService.deleteDeclaration(id);
        return ResponseEntity.ok("Declaration deleted");

    }
}
