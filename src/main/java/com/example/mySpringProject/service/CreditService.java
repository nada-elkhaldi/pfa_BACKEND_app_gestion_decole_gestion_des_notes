package com.example.mySpringProject.service;

import com.example.mySpringProject.dto.CreditDto;
import com.example.mySpringProject.model.Credit;

import java.util.List;

public interface CreditService {

    CreditDto demanderCreditPanne(CreditDto creditDto);
    CreditDto demanderCredit(CreditDto creditDto);
    List<CreditDto> getCredits();
    Credit getCreditById(Integer id);
    void deleteCredit(Integer id);

    List<Credit> findCreditsByUserId(Integer userId);

    Double getTotalMontantDemande();
}
