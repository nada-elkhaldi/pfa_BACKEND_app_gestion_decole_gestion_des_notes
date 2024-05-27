package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.PlanificationMatiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanificationRepository extends JpaRepository<PlanificationMatiere, Integer> {
}
