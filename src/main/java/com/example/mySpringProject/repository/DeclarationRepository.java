package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Declaration;
import com.example.mySpringProject.model.Panne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeclarationRepository extends JpaRepository<Declaration, Integer> {

    Declaration findByPanne(Panne savedPanne);
}
