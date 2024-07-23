package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Feu;
import com.example.mySpringProject.model.TauDisposability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TauxDisponibiliteRepository extends JpaRepository<TauDisposability, Integer> {
    TauDisposability findByFeu(Feu feu);
}