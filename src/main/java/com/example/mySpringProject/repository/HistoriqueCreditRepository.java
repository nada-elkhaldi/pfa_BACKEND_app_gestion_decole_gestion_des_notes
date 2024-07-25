package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.HistoriqueCredit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriqueCreditRepository extends JpaRepository<HistoriqueCredit, Integer> {
}
