package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Credit;
import com.example.mySpringProject.model.Panne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer> {

    @Query("SELECT c FROM Credit c WHERE c.demandeur.id = :userId")
    List<Credit> findCreditsByUserId(@Param("userId") Integer userId);


    @Query("SELECT SUM(c.montantDemande) FROM Credit c")
    Double getTotalMontantDemande();



}
