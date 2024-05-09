package com.example.mySpringProject.controller;


import com.example.mySpringProject.model.AuthenticationResponse;
import com.example.mySpringProject.model.Email;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.service.AuthenticationService;
import com.example.mySpringProject.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/utilisateurs", method = {RequestMethod.POST, RequestMethod.OPTIONS})
@RestController

public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;

    public AuthenticationController(AuthenticationService authenticationService, EmailService emailService) {
        this.authenticationService = authenticationService;
        this.emailService = emailService;
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUser (@RequestBody User user) {
        return ResponseEntity.ok(authenticationService.createUser(user));
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User user) {
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody Email email) {
        return emailService.sendMail(email);
    }

@GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(authenticationService.getUserById(id));

    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = authenticationService.getAllUsers();
        return  ResponseEntity.ok(users);
    }
    }





