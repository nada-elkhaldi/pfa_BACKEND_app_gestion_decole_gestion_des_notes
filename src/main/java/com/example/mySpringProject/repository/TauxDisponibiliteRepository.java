package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Feu;
import com.example.mySpringProject.model.TauDisposability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TauxDisponibiliteRepository extends JpaRepository<TauDisposability, Integer> {
    TauDisposability findByFeu(Feu feu);

    @Query("SELECT t FROM TauDisposability t WHERE t.feu.province.region.id = :regionId")
    List<TauDisposability> findByFeuRegionId(Integer regionId);
}