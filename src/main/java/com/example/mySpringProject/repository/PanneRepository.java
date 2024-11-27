package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Feu;
import com.example.mySpringProject.model.Panne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PanneRepository extends JpaRepository<Panne, Integer> {

    List<Panne> findByEtatGeneral(String etatGeneral);

    @Query("SELECT p FROM Panne p WHERE p.province.region.id = :regionId")
    List<Panne> findPannesByRegionId(@Param("regionId") Integer regionId);

    List<Panne> findByTraitee(Integer traitee);
    List<Panne> findByArchive(Integer archive);

    List<Panne> findByFeu(Feu feu);

    long count();

    long countByTraitee(Integer traitee);
}
