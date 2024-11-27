package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Feu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeuRepository extends JpaRepository<Feu, Integer> {

    List<Feu> findByProvinceId( Integer idProvince);
    @Query("SELECT f FROM Feu f WHERE f.province.id = :idProvince")
    List<Feu> findFeuxByProvince(

            @Param("idProvince") Integer idProvince);
}
