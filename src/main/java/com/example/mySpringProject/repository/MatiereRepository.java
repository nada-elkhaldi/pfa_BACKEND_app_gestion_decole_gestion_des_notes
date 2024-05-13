package com.example.mySpringProject.repository;


import com.example.mySpringProject.model.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatiereRepository extends JpaRepository<Matiere, Integer> {
}
