package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Feu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeuRepository extends JpaRepository<Feu, Integer> {

    List<Feu> findByRegionIdAndProvinceId(Integer idRegion, Integer idProvince);
    @Query("SELECT f FROM Feu f WHERE f.region.id = :idRegion AND f.province.id = :idProvince")
    List<Feu> findFeuxByRegionAndProvince(
            @Param("idRegion") Integer idRegion,
            @Param("idProvince") Integer idProvince);
}
