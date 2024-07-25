package com.example.mySpringProject.service;

import com.example.mySpringProject.model.HistoriqueCredit;

import java.util.List;

public interface HistoriqueCreditService  {

    List<HistoriqueCredit> getAllHistoriqueCredit();
    void delegateCredit(Integer idDemande, Integer idBudget);
}
