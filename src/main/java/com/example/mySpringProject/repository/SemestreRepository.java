package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemestreRepository extends JpaRepository<Semestre, Integer> {
}
