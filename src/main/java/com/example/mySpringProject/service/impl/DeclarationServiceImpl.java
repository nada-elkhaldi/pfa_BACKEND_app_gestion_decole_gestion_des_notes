package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.model.Declaration;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.repository.DeclarationRepository;
import com.example.mySpringProject.repository.UserRepository;
import com.example.mySpringProject.service.DeclarationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeclarationServiceImpl implements DeclarationService {


    private  final DeclarationRepository declarationRepository;
    private  final UserRepository userRepository;

    @Autowired
    public DeclarationServiceImpl(DeclarationRepository declarationRepository, UserRepository userRepository) {
        this.declarationRepository = declarationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Declaration addDeclaration(Declaration request) {
        Declaration declaration1 = new Declaration();

        User declarant = userRepository.findById(request.getDeclarant().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        declaration1.setDeclarant(declarant);
        declaration1.setTyeDeclaration(request.getTyeDeclaration());
        declaration1.setDateDeclaration(LocalDate.now());
        declaration1.setEtat("En attente..");
        declarationRepository.save(declaration1);
        return declaration1;
    }

    @Override
    public List<Declaration> getAlldeclarations() {
        List<Declaration> declarations = declarationRepository.findAll();
        return declarations.stream().map((declaration)-> declaration).collect(Collectors.toList());
    }

    @Override
    public Declaration getDeclarationById(Integer id) {
        return declarationRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Declaration with id " + id + " not found"));

    }
    @Override
    public void deleteDeclaration(Integer id) {
        Declaration declaration = declarationRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Declaration with id " + id + " not found")
        );
        declarationRepository.deleteById(id);
    }
}
