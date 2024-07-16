package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Panne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PanneRepository extends JpaRepository<Panne, Integer> {

    List<Panne> findByEtatGeneral(String etatGeneral);
}
