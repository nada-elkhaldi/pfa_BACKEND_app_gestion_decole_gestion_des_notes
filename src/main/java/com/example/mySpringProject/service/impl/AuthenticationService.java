package com.example.mySpringProject.service.impl;


import com.example.mySpringProject.controller.PasswordGenerator;
import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.model.*;
import com.example.mySpringProject.repository.ClasseRepository;
import com.example.mySpringProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private  final ClasseRepository classeRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator; // Ajout de la dépendance PasswordGenerator

    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;


    @Autowired
    public AuthenticationService(UserRepository repository,
                                 ClasseRepository classeRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 PasswordGenerator passwordGenerator,
                                 AuthenticationManager authenticationManager,
                                 EmailService emailService) {
        this.repository = repository;
        this.classeRepository = classeRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.passwordGenerator = passwordGenerator;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }


public AuthenticationResponse createUser(List<User> requests) {
    if (requests.isEmpty()) {
        throw new IllegalArgumentException("La liste des utilisateurs est vide.");
    }
    List<String> tokens = new ArrayList<>(requests.size());
    for (User request : requests) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        Classe classe = request.getClasse();
        if (classe != null && classe.getId() != null) {
            classe = classeRepository.getById(classe.getId());
            user.setClasse(classe);
        }
        //generation de mot de passe
        String generatedPassword = passwordGenerator.generatePassword();
        String encryptedPassword = passwordGenerator.encryptPassword(generatedPassword);
        user.setPassword(encryptedPassword);
        user.setRole(request.getRole());
        repository.save(user);
        //generation du jeton
        String token = jwtService.generateToken(user);
        tokens.add(token);

        // envoi de l e-mail d'authentification
        Email email = new Email();
        email.setRecipient(user.getEmail());
        email.setSubject("Informations sur votre compte: ");
        email.setBody(
                "Bonjour " + user.getRole() + "\n" +
                        "       Votre email est : " + user.getEmail() + "\n" +
                        "       Votre mot de passe est : " + generatedPassword);

        String result = emailService.sendMail(email);
        if (!result.equals("Email sent")) {
            throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
        }
    }
    return new AuthenticationResponse(tokens.toString());
}

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public User getUserById(Integer id) {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User with id " + id + " not found"));

    }

    public User getEtudiantById(int id) {
        return repository.findByIdAndRole(id, Role.ETUDIANT);


    }

    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream().map((user)-> user).collect(Collectors.toList());
    }


    public List<User> getAllEtudiants() {
        return repository.getAllEtudiants();
    }

    public List<User> getAllEnseignants() {
        return repository.getAllEnseignants();
    }


    public User updateUser(Integer id , User request) {
        User user= repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User with id " + id + " not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        User updatedUser= repository.save(user);

        return updatedUser;

    }
    public void deleteUser (Integer id) {

        User user= repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User with id " + id + " not found"));
        repository.deleteById(id);
    }


    public void saveEtudiant(User etudiant) {
        repository.save(etudiant);
    }

    public List<User> getEtudiantsParClasseEtGroupe(Integer classeId, Integer groupeId) {
        return repository.findByClasseIdAndGroupeId(classeId, groupeId);
    }


    public boolean changePassword(Integer userId, String newPassword) {
        Optional<User> optionalUser = repository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Encrypter le nouveau mot de passe
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);

            // Enregistrement du nouvel utilisateur
            repository.save(user);

            return true;
        }
        return false;
    }
}
