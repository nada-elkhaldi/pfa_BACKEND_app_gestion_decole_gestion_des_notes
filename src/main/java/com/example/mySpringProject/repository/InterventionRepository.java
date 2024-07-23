package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterventionRepository extends JpaRepository<Intervention, Integer> {
}
