package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Feu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeuRepository extends JpaRepository<Feu, Integer> {
}
