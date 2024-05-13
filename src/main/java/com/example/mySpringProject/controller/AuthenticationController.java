package com.example.mySpringProject.controller;


import com.example.mySpringProject.model.AuthenticationResponse;
import com.example.mySpringProject.model.Email;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.service.impl.AuthenticationService;
import com.example.mySpringProject.service.impl.EmailService;
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
    public ResponseEntity<AuthenticationResponse> createUser (@RequestBody  List<User> user) {

        AuthenticationResponse response = authenticationService.createUser(user);
        return ResponseEntity.ok(response);
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

    @PutMapping("{id}")
     public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User updatedUser) {
       User user= authenticationService.updateUser(id, updatedUser);
       return ResponseEntity.ok(user);

     }

     @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
          authenticationService.deleteUser(id);
         return ResponseEntity.ok("User deleted");

    }
    }





