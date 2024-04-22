package com.example.mySpringProject.controller;


import com.example.mySpringProject.model.AuthenticationResponse;
import com.example.mySpringProject.model.Email;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.service.AuthenticationService;
import com.example.mySpringProject.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/utilisateurs", method = {RequestMethod.POST, RequestMethod.OPTIONS})
@RestController

public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;

    public AuthenticationController(AuthenticationService authenticationService, EmailService emailService) {
        this.authenticationService = authenticationService;
        this.emailService = emailService;
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User user) {
        return ResponseEntity.ok(authenticationService.register(user));
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User user) {
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody Email email) {
        return emailService.sendMail(email);
    }

    }





