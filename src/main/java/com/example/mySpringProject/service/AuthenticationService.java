package com.example.mySpringProject.service;


import com.example.mySpringProject.controller.PasswordGenerator;
import com.example.mySpringProject.dto.GroupeDto;
import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.model.AuthenticationResponse;
import com.example.mySpringProject.model.Email;
import com.example.mySpringProject.model.Groupe;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;

    private final PasswordGenerator passwordGenerator; // Ajout de la dépendance PasswordGenerator

    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(UserRepository repository, JwtService jwtService, PasswordGenerator passwordGenerator, AuthenticationManager authenticationManager, EmailService emailService) {
        this.repository = repository;

        this.jwtService = jwtService;
        this.passwordGenerator = passwordGenerator;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public AuthenticationResponse createUser(User request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        //generation de mot de passe
        String generatedPassword = passwordGenerator.generatePassword();
        System.out.println(generatedPassword);
        String encryptedPassword = passwordGenerator.encryptPassword(generatedPassword);
        user.setPassword(encryptedPassword);
        user.setRole(request.getRole());
        repository.save(user);

        String token = jwtService.generateToken(user);
        //envoyer l'e-mail d'authentification
        Email email = new Email();
        email.setRecipient(user.getEmail());
        email.setSubject("Informations sur votre compte");
        email.setBody(
                "Bonjour "+user.getRole()+ "\n" +
                "Votre email est : " + user.getEmail() + "\n" +
                "Votre mot de passe est : " + generatedPassword);

        String result = emailService.sendMail(email);
        if (result.equals("Email sent")) {
            return new AuthenticationResponse(token);
        } else {
            throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
        }
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

    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream().map((user)-> user).collect(Collectors.toList());

    }
}
