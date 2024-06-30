package com.example.mySpringProject.controller;


import com.example.mySpringProject.dto.AddEtudiantAClasseDto;
import com.example.mySpringProject.dto.ClasseDto;
import com.example.mySpringProject.dto.GroupeDto;
import com.example.mySpringProject.model.*;

import com.example.mySpringProject.service.impl.AuthenticationService;
import com.example.mySpringProject.service.impl.ClasseServiceImpl;
import com.example.mySpringProject.service.impl.EmailService;
import com.example.mySpringProject.service.impl.GroupeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1", method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
@RestController

public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    private final ClasseServiceImpl classeService;
    private final GroupeServiceImpl groupeService;


    public AuthenticationController(AuthenticationService authenticationService, EmailService emailService, ClasseServiceImpl classeService, GroupeServiceImpl groupeService) {
        this.authenticationService = authenticationService;
        this.emailService = emailService;
        this.classeService = classeService;
        this.groupeService = groupeService;
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUser (@RequestBody  List<User> user) {

        AuthenticationResponse response = authenticationService.createUser(user);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User user) {
        AuthenticationResponse response = authenticationService.authenticate(user);

        return ResponseEntity.ok(response);
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

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/etudiants")
    public List<User> getAllEtudiants() {
        return authenticationService.getAllEtudiants();
    }
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/enseignants")
    public List<User> getAllEnseignants() {
        return authenticationService.getAllEnseignants();
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

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/etudiants/ajouter-a-classe")
    public ResponseEntity<Void> ajouterEtudiantAClasse(@RequestBody AddEtudiantAClasseDto dto) {
        User student = authenticationService.getEtudiantById(dto.getEtudiantId());
        Classe classe = classeService.getClasseById(dto.getClasseId());
        Groupe groupe = groupeService.getGroupeById(dto.getGroupeId());
        student.setClasse(classe);
        student.setGroupe(groupe);
        authenticationService.saveEtudiant(student);
        return ResponseEntity.ok().build();
    }


    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/classe/{classeId}/groupe/{groupeId}")
    public List<User> getEtudiantsParClasseEtGroupe(@PathVariable Integer classeId, @PathVariable Integer groupeId) {
        return authenticationService.getEtudiantsParClasseEtGroupe(classeId, groupeId);
    }



    //changemant du mot de passe
    @PutMapping("/utilisateurs/{id}/motdepasse")
    public ResponseEntity<String> changerMotDePasse(@PathVariable Integer id, @RequestBody ChangePasswordRequest request) {
        String nouveauMotDePasse = request.getNewPassword();

        if (authenticationService.changePassword(id, nouveauMotDePasse)) {
            return ResponseEntity.ok("Mot de passe changé avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    }






