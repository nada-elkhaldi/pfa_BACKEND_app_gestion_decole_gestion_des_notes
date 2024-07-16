package com.example.mySpringProject.service.impl;


import com.example.mySpringProject.dto.CreditDto;
import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.mapper.CreditMapper;
import com.example.mySpringProject.model.*;
import com.example.mySpringProject.repository.BudgetRepository;
import com.example.mySpringProject.repository.CreditRepository;
import com.example.mySpringProject.repository.FeuRepository;
import com.example.mySpringProject.service.CreditService;
import com.example.mySpringProject.service.FeuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditServiceImpl implements CreditService {

    private final BudgetRepository budgetRepository;
    private final CreditRepository creditRepository;
    private final FeuService feuService;
    private final FeuRepository feuRepository;
    private final EmailService emailService;


    @Autowired
    public CreditServiceImpl(BudgetRepository budgetRepository, CreditRepository creditRepository, FeuService feuService, FeuRepository feuRepository, EmailService emailService) {
        this.budgetRepository = budgetRepository;
        this.creditRepository = creditRepository;

        this.feuService = feuService;
        this.feuRepository = feuRepository;
        this.emailService = emailService;
    }


    @Override
    public CreditDto demanderCredit(CreditDto creditDto) {
         Credit credit = CreditMapper.mapToCredit(creditDto);
        Feu feu = feuRepository.findById(creditDto.getIdFeu())
                .orElseThrow(() -> new RuntimeException("Feu not found"));
        Budget budget = budgetRepository.findById(creditDto.getIdBudget())
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        credit.setFeu(feu);
        credit.setBudget(budget);
        Credit savedCredit= creditRepository.save(credit);
        Email email = new Email();
        email.setRecipient(savedCredit.getEmailDPDPM());
        email.setSubject("Notification de Délégation de Crédit");

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Bonjour DPDPM,\n\n")
                .append("Nous vous informons qu'un crédit doit être délégué pour procéder à la résolution de la panne pré-mentionnée. Voici les informations nécessaires :\n\n")
                .append("   - Nature de crédit : ").append(savedCredit.getNatureCredit()).append("\n")
                .append("   - Nom du phare : ")
                .append(savedCredit.getFeu().getNomLocalisation()).append(" (Position : ").append(savedCredit.getFeu().getPosition()).append(")\n")
                .append("   - Montant : ").append(savedCredit.getMontant()).append(" MAD\n\n")
                .append("   - Date de demande : ").append(savedCredit.getDateDemande()).append("\n\n")
                .append("Veuillez prendre les mesures nécessaires pour finaliser cette délégation de crédit.\n\n")
                .append("Cordialement,\n")
                .append("L'équipe de gestion des feux");

        email.setBody(emailBody.toString());
        String result = emailService.sendMail(email);
        if (!result.equals("Email sent")) {
            throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
        }
        return CreditMapper.mapToCreditDto(savedCredit);

    }

    @Override
    public List<CreditDto> getCredits() {
        List<Credit> credits = creditRepository.findAll();
        return credits.stream().map((credit)-> CreditMapper.mapToCreditDto(credit))
                .collect(Collectors.toList());
    }

    @Override
    public Credit getCreditById(Integer id) {
        return creditRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Credit with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Credit delegateCredit(Integer creditId, Double montant) {
        Credit credit = creditRepository.findById(creditId).orElseThrow(() -> new RuntimeException("Credit not found"));
        Budget budget = credit.getBudget();
        if (budget.getBudget() >= montant) {
            budget.setBudget(budget.getBudget() - montant);
        } else {
            throw new RuntimeException("Insufficient budget for " + budget.getPrevisionOperation());
        }

        credit.setEtat("delegue");
        credit.setDateDelegation(LocalDate.now());

        budgetRepository.save(budget);
        return creditRepository.save(credit);
    }


    @Override
    public void deleteCredit(Integer id) {
        Credit credit = creditRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Credit with id " + id + " not found")
        );
        creditRepository.deleteById(id);
    }
}
