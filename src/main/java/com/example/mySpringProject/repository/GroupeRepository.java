package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeRepository extends JpaRepository<Groupe, Integer> {
}
