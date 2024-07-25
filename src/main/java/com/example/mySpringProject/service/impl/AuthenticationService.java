package com.example.mySpringProject.service.impl;


import com.example.mySpringProject.controller.PasswordGenerator;
import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.model.*;
import com.example.mySpringProject.repository.ProvinceRepository;
import com.example.mySpringProject.repository.RegionRepository;
import com.example.mySpringProject.repository.RoleRepository;
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
    private final RegionRepository regionRepository;
    private final ProvinceRepository provinceRepository;
    private final RoleRepository roleRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator;

    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;


    @Autowired
    public AuthenticationService(UserRepository repository, RegionRepository regionRepository, ProvinceRepository provinceRepository, RoleRepository roleRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 PasswordGenerator passwordGenerator,
                                 AuthenticationManager authenticationManager,
                                 EmailService emailService) {
        this.repository = repository;
        this.regionRepository = regionRepository;
        this.provinceRepository = provinceRepository;
        this.roleRepository = roleRepository;
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
            if (repository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("L'adresse email " + request.getEmail() + " existe déjà.");
            }

            Region region =  regionRepository.findById(request.getRegion().getId())
                    .orElseThrow(() -> new RuntimeException("Region not found"));
            Province province = provinceRepository.findById(request.getProvince().getId())
                    .orElseThrow(() -> new RuntimeException("Province not found"));

            Role role = roleRepository.findById(request.getRole().getId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            String encryptedPassword = passwordGenerator.encryptPassword(request.getPassword());
            user.setPassword(encryptedPassword);
            user.setRole(role);
            user.setOrganisme(request.getOrganisme());
            user.setRegion(region);
            user.setProvince(province);

            // Génération du jeton
            String token = jwtService.generateToken(user);
            tokens.add(token);

            // Exemple de création d'un email dans votre service existant
            Email email = new Email();
            email.setRecipient(user.getEmail());
            email.setSubject("Informations sur votre compte");

            StringBuilder emailBody = new StringBuilder();
            emailBody.append("Bonjour ").append(user.getRole().getRoleName()).append(",\n\n")
                    .append("Nous sommes heureux de vous accueillir dans notre système.\n\n")
                    .append("Voici vos informations de connexion :\n")
                    .append("   - Adresse email : ").append(user.getEmail()).append("\n")
                    .append("   - Mot de passe : ").append(request.getPassword()).append("\n\n")
                    .append("Cordialement,\n")
                    .append("L'équipe de support");

            email.setBody(emailBody.toString());

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

    public List<User> getAllDPDPMs() {
        return repository.getAllDPDPMUsers();
    }

    public List<User> getAllDHOCs() {
        return repository.getAllDHOCUsers();
    }

    public List<User> getAllDPEs() {
        return repository.getAllDPEUsers();
    }

    public List<User> getAllAutoPorts() {
        return repository.getAllAutoPortUsers();
    }

    public User getUserById(int id) {
        return repository.findById (id);


    }

    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream().map((user)-> user).collect(Collectors.toList());
    }




//   // public List<User> getAllEnseignants() {
//        return repository.getAllEnseignants();
//    }


    public User updateUser(Integer id , User request) {
        User user= repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User with id " + id + " not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setOrganisme(request.getOrganisme());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRegion(request.getRegion());
        User updatedUser= repository.save(user);

        return updatedUser;

    }
    public void deleteUser (Integer id) {

        User user= repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User with id " + id + " not found"));
        repository.deleteById(id);
    }


//    public void saveEtudiant(User etudiant) {
//        repository.save(etudiant);
//    }




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
