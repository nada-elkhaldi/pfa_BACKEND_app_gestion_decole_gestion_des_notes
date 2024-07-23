package com.example.mySpringProject.service;
import com.example.mySpringProject.model.Declaration;

import java.util.List;

public interface DeclarationService {

    Declaration addDeclaration(Declaration declaration);
    List<Declaration> getAlldeclarations();
    Declaration getDeclarationById(Integer id);

    void deleteDeclaration(Integer id);
}
