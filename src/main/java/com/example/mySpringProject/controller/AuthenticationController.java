package com.example.mySpringProject.controller;


import com.example.mySpringProject.model.*;

import com.example.mySpringProject.service.impl.AuthenticationService;
import com.example.mySpringProject.service.impl.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1", method = {RequestMethod.POST,RequestMethod.GET, RequestMethod.PUT, RequestMethod.OPTIONS})
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
    public ResponseEntity<AuthenticationResponse> createUser(
            @RequestBody List<User> user,
            @RequestParam(value = "isActive", defaultValue = "false") boolean isActive) {

        AuthenticationResponse response = authenticationService.createUser(user,isActive);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User user) {
        AuthenticationResponse response = authenticationService.authenticate(user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody Email email) {
        return emailService.sendMail(email);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@GetMapping("/utilisateur/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(authenticationService.getUserById(id));

    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User updatedUser) {
        User user= authenticationService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);

    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/utilisateurs")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = authenticationService.getAllUsers();
        return  ResponseEntity.ok(users);
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/utilisateursDPDPM")
    public ResponseEntity<List<User>> getAllDPDPMUsers() {
        List<User> users = authenticationService.getAllDPDPMs();
        return  ResponseEntity.ok(users);
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/utilisateursDHOC")
    public ResponseEntity<List<User>> getAllDHOCUsers() {
        List<User> users = authenticationService.getAllDHOCs();
        return  ResponseEntity.ok(users);
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/utilisateursDPE")
    public ResponseEntity<List<User>> getAllDPEUsers() {
        List<User> users = authenticationService.getAllDPEs();
        return  ResponseEntity.ok(users);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/utilisateursDRE")
    public ResponseEntity<List<User>> getAllDREUsers() {
        List<User> users = authenticationService.getAllDRE();
        return  ResponseEntity.ok(users);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/utilisateursAUTOPORT")
    public ResponseEntity<List<User>> getAllAUTOPORTUsers() {
        List<User> users = authenticationService.getAllAutoPorts();
        return  ResponseEntity.ok(users);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
     @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
          authenticationService.deleteUser(id);
         return ResponseEntity.ok("User deleted");

    }


    //changemant du mot de passe
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PutMapping("/utilisateurs/{id}/motdepasse")
    public ResponseEntity<String> changerMotDePasse(@PathVariable Integer id, @RequestBody ChangePasswordRequest request) {
        String nouveauMotDePasse = request.getNewPassword();

        if (authenticationService.changePassword(id, nouveauMotDePasse)) {
            return ResponseEntity.ok("Mot de passe changé avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/deactivate/{userId}")
    public void deactivateUser(@PathVariable Integer userId) {
        authenticationService.deactivateUser(userId);
    }


    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/activate/{userId}")
    public ResponseEntity<String> activateUser(@PathVariable Integer userId) {
        try {
            authenticationService.activateUser(userId);
            return ResponseEntity.ok("Utilisateur activé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'activation de l'utilisateur.");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/inactive")
    public ResponseEntity<List<User>> getAllInactiveUsers() {
        List<User> inactiveUsers = authenticationService.getAllInactiveUsers();
        if (inactiveUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inactiveUsers);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/users-count")
    public long getUserCount() {
        return authenticationService.getUserCount();
    }
    }






