package com.example.mySpringProject.service;

import com.example.mySpringProject.dto.CreditDto;
import com.example.mySpringProject.model.Credit;

import java.util.List;

public interface CreditService {

    CreditDto demanderCredit(CreditDto creditDto);
    List<CreditDto> getCredits();
    Credit getCreditById(Integer id);
    Credit delegateCredit(Integer creditId, Double montant);
    void deleteCredit(Integer id);
}