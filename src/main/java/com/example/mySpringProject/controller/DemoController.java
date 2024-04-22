package com.example.mySpringProject.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class DemoController {

    @GetMapping("/api/v1/demo-controller")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/api/v1/etudiant")
    public ResponseEntity<String> etudiant(){
        return ResponseEntity.ok("Hello from etudiant endpoint");
    }

}
