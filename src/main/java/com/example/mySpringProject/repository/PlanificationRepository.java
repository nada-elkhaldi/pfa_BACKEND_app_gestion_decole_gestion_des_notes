package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Classe;
import com.example.mySpringProject.model.Groupe;
import com.example.mySpringProject.model.PlanificationMatiere;
import com.example.mySpringProject.model.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanificationRepository extends JpaRepository<PlanificationMatiere, Integer> {
    List<PlanificationMatiere> findBySemestreAndClasseAndGroupe(Semestre semestre, Classe classe, Groupe groupe);
}
