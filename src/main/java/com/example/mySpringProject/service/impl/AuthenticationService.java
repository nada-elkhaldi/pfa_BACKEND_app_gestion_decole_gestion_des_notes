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


    public AuthenticationResponse createUser(List<User> requests, boolean isActive) {
        if (requests.isEmpty()) {
            throw new IllegalArgumentException("La liste des utilisateurs est vide.");
        }
        List<String> tokens = new ArrayList<>(requests.size());
        for (User request : requests) {
            if (repository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("L'adresse email " + request.getEmail() + " existe déjà.");
            }


            Province province = provinceRepository.findById(request.getProvince().getId())
                    .orElseThrow(() -> new RuntimeException("Province not found"));

            Role role = roleRepository.findById(request.getRole().getId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setActive(isActive);
            user.setEtat("Pas activé");
            String encryptedPassword = passwordGenerator.encryptPassword(request.getPassword());
            user.setPassword(encryptedPassword);
            user.setRole(role);
            user.setOrganisme(request.getOrganisme());
            user.setProvince(province);

            repository.save(user);

            String token = jwtService.generateToken(user);
            tokens.add(token);

            Email email = new Email();
            email.setRecipient(user.getEmail());
            email.setSubject("Informations sur votre compte");

            StringBuilder emailBody = new StringBuilder();
            emailBody.append("<p>Bonjour ").append(user.getRole().getRoleName()).append(",</p>")
                    .append("<p>Voici vos informations de connexion :</p>")
                    .append("<ul>")
                    .append("   <li>Adresse email : ").append(user.getEmail()).append("</li>")
                    .append("   <li>Mot de passe : ").append(request.getPassword()).append("</li>")
                    .append("</ul>")
                    .append("<p><span style='color:red;'>NB: Votre compte est actuellement en attente de vérification par un administrateur.</span></p>")
                    .append("<p><span style='color:red;'>NB: Nous vous informerons par e-mail dès que votre compte sera activé.</span></p>")
                    .append("<p>Cordialement,</p>")
                    .append("<p>L'équipe de support</p>");

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
    public List<User> getAllDRE() {
        return repository.getAllDREUsers();
    }

    public User getUserById(int id) {
        return repository.findById (id);


    }

    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream().map((user)-> user).collect(Collectors.toList());
    }



    public User updateUser(Integer id , User request) {
        User user= repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User with id " + id + " not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setOrganisme(request.getOrganisme());
      
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
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            repository.save(user);

            return true;
        }
        return false;
    }


    public void deactivateUser(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setActive(false);
        user.setEtat("Pas activé");
        repository.save(user);
        sendDeactivationEmail(user);
    }

    private void sendDeactivationEmail(User user) {
        Email email = new Email();
        email.setRecipient(user.getEmail());
        email.setSubject("Votre compte a été désactivé");

        StringBuilder emailBody = new StringBuilder();
        emailBody
                .append("<p>Bonjour ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",</p>")
                .append("<p>Nous souhaitons vous informer que votre compte a été désactivé.</p>")
                .append("<p>Pour toute question, veuillez contacter le support.</p>")
                .append("<p>Cordialement,<br/>")
                .append("L'équipe de support</p>");


        email.setBody(emailBody.toString());

        String result = emailService.sendMail(email);
        if (!result.equals("Email sent")) {
            throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
        }
    }


    public void activateUser(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (user.isActive()) {
            throw new RuntimeException("Le compte est déjà actif");
        }

        user.setActive(true);
        user.setEtat("Activé");
        repository.save(user);
        sendActivationEmail(user);
    }

    private void sendActivationEmail(User user) {
        Email email = new Email();
        email.setRecipient(user.getEmail());
        email.setSubject("Votre compte a été activé");

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<p>Bonjour ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",</p>")
                .append("<p>Nous souhaitons vous informer que votre compte a été activé.</p>")
                .append("<p>Vous pouvez maintenant vous connecter avec vos informations.</p>")
                .append("<p>Cordialement,<br/>")
                .append("L'équipe de support</p>");


        email.setBody(emailBody.toString());

        String result = emailService.sendMail(email);
        if (!result.equals("Email sent")) {
            throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
        }
    }


    public List<User> getAllInactiveUsers() {
        return repository.findByActive(false);
    }

    public long getUserCount() {
        return repository.count();
    }
}
