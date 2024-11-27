package com.example.mySpringProject.service.impl;


import com.example.mySpringProject.model.Budget;
import com.example.mySpringProject.model.Credit;
import com.example.mySpringProject.model.HistoriqueCredit;
import com.example.mySpringProject.repository.BudgetRepository;
import com.example.mySpringProject.repository.CreditRepository;
import com.example.mySpringProject.repository.HistoriqueCreditRepository;
import com.example.mySpringProject.service.HistoriqueCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoriqueCreditServiceImpl implements HistoriqueCreditService {


    private final CreditRepository creditRepository;
    private final  BudgetRepository budgetRepository;
    private final HistoriqueCreditRepository historiqueCreditRepository;

    @Autowired
    public HistoriqueCreditServiceImpl(CreditRepository creditRepository, BudgetRepository budgetRepository, HistoriqueCreditRepository historiqueCreditRepository) {
        this.creditRepository = creditRepository;
        this.budgetRepository = budgetRepository;
        this.historiqueCreditRepository = historiqueCreditRepository;
    }

    @Override
    public List<HistoriqueCredit> getAllHistoriqueCredit() {
        List<HistoriqueCredit> credits = historiqueCreditRepository.findAll();
        return credits.stream().map((credit)-> credit).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delegateCredit(Integer idDemande, Integer idBudget) {
        Credit credit = creditRepository.findById(idDemande)
                .orElseThrow(() -> new RuntimeException("Demande de crédit non trouvée"));

        Budget budget = budgetRepository.findById(idBudget)
                .orElseThrow(() -> new RuntimeException("Budget non trouvé"));

        // verifier si le montant demandé peut être alloué
        if (credit.getMontantDemande() > budget.getMontantDisponible()) {
            throw new RuntimeException("Montant demandé dépasse le montant disponible du budget");
        }

        // mettre à jour le budget
        double montantDemande = credit.getMontantDemande();
        budget.setMontantUtilise(budget.getMontantUtilise() + montantDemande);
        budget.setMontantDisponible(budget.getMontantDisponible() - montantDemande);

        budgetRepository.save(budget);

        // mettre à jour l'état de la demande de crédit
        credit.setEtat("Délégué");
        creditRepository.save(credit);
        //
        HistoriqueCredit historiqueCredit = new HistoriqueCredit();
        historiqueCredit.setCreditDelegue(montantDemande);
        historiqueCredit.setDateDelegation(LocalDate.now());
        historiqueCredit.setCreditDemande(credit);
        historiqueCredit.setBudget(budget);

        historiqueCreditRepository.save(historiqueCredit);
    }

    @Override
    public Double getTotalCreditDelegue() {
        return historiqueCreditRepository.getTotalCreditDelegue();
    }
}
