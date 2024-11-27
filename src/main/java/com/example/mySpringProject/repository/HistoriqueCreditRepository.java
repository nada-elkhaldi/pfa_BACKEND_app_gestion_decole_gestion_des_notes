package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.HistoriqueCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HistoriqueCreditRepository extends JpaRepository<HistoriqueCredit, Integer> {

    @Query("SELECT SUM(c.creditDelegue) FROM HistoriqueCredit c")
    Double getTotalCreditDelegue();
}
