package com.example.mySpringProject.service.impl;


import com.example.mySpringProject.dto.CreditDto;
import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.mapper.CreditMapper;
import com.example.mySpringProject.model.*;
import com.example.mySpringProject.repository.*;
import com.example.mySpringProject.service.CreditService;
import com.example.mySpringProject.service.FeuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditServiceImpl implements CreditService {

    private final BudgetRepository budgetRepository;
    private final CreditRepository creditRepository;
    private final FeuService feuService;
    private final FeuRepository feuRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PanneRepository panneRepository;


    @Autowired
    public CreditServiceImpl(BudgetRepository budgetRepository, CreditRepository creditRepository, FeuService feuService, FeuRepository feuRepository, EmailService emailService, UserRepository userRepository, PanneRepository panneRepository) {
        this.budgetRepository = budgetRepository;
        this.creditRepository = creditRepository;

        this.feuService = feuService;
        this.feuRepository = feuRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.panneRepository = panneRepository;
    }


    @Override
    public CreditDto demanderCreditPanne(CreditDto creditDto) {
         Credit credit = CreditMapper.mapToCredit(creditDto);

        User demandeur = userRepository.findById(creditDto.getIdDemandeur())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Panne panne = panneRepository.findById(creditDto.getIdPanne())
                .orElseThrow(() -> new RuntimeException("Panne not found"));

        credit.setDemandeur(demandeur);
        credit.setPanne(panne);
        Credit savedCredit= creditRepository.save(credit);

        List<String> roles = Arrays.asList("DPDPM", "DPDPM-User");
        List<User> dpdpmUsers = userRepository.findByRole_NameIn(roles);

        for (User user : dpdpmUsers) {
            Email email = new Email();
            email.setRecipient(user.getEmail());
            email.setSubject("Notification de Délégation de Crédit");

            StringBuilder emailBody = new StringBuilder();
            emailBody.append("<html>")
                    .append("<head><meta charset=\"UTF-8\"></head>")
                    .append("<body>")
                    .append("<p>Bonjour DPDPM,</p>")
                    .append("<p>Nous vous informons qu'un crédit doit être délégué pour procéder à la résolution de la panne pré-mentionnée. Voici les informations nécessaires :</p>")
                    .append("<ul>")
                    .append("<li><strong>Nom du phare :</strong> ").append(savedCredit.getPanne().getFeu().getNomLocalisation())
                    .append(" (Position : ").append(savedCredit.getPanne().getFeu().getPosition()).append(")</li>")
                    .append("<li><strong>Montant :</strong> ").append(savedCredit.getMontantDemande()).append(" MAD</li>")
                    .append("<li><strong>Date de demande :</strong> ").append(savedCredit.getDateDemande()).append("</li>")
                    .append("</ul>")
                    .append("<p>Veuillez prendre les mesures nécessaires pour finaliser cette délégation de crédit.</p>")
                    .append("<p>Cordialement,</p>")
                    .append("<p>L'équipe de gestion des feux</p>")
                    .append("</body>")
                    .append("</html>");


            email.setBody(emailBody.toString());
            String result = emailService.sendMail(email);
            if (!result.equals("Email sent")) {
                throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
            }
        }

        return CreditMapper.mapToCreditDto(savedCredit);

    }

    @Override
    public CreditDto demanderCredit(CreditDto creditDto) {
        Credit credit = CreditMapper.mapToCredit(creditDto);

        User demandeur = userRepository.findById(creditDto.getIdDemandeur())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Feu feu = feuRepository.findById(creditDto.getIdFeu())
                .orElseThrow(() -> new RuntimeException("Feu not found"));

        credit.setDemandeur(demandeur);
        credit.setFeu(feu);
        Credit savedCredit= creditRepository.save(credit);

        List<String> roles = Arrays.asList("DPDPM", "DPDPM-User");
        List<User> dpdpmUsers = userRepository.findByRole_NameIn(roles);

        for (User user : dpdpmUsers) {
            Email email = new Email();
            email.setRecipient(user.getEmail());
            email.setSubject("Notification de Délégation de Crédit");

            StringBuilder emailBody = new StringBuilder();
            emailBody.append("<p>Bonjour DPDPM,</p>")
                    .append("<p>Nous vous informons qu'un crédit doit être délégué pour procéder à la résolution de la panne pré-mentionnée. Voici les informations nécessaires :</p>")
                    .append("<ul>")
                    .append("<li><strong>Nom du phare :</strong> ").append(savedCredit.getFeu().getNomLocalisation()).append(" (Position : ").append(savedCredit.getFeu().getPosition()).append(")</li>")
                    .append("<li><strong>Montant :</strong> ").append(savedCredit.getMontantDemande()).append(" MAD</li>")
                    .append("<li><strong>Date de demande :</strong> ").append(savedCredit.getDateDemande()).append("</li>")
                    .append("</ul>")
                    .append("<p>Veuillez prendre les mesures nécessaires pour finaliser cette délégation de crédit.</p>")
                    .append("<p>Cordialement,<br/>")
                    .append("L'équipe de gestion des feux</p>");

            email.setBody(emailBody.toString());
            String result = emailService.sendMail(email);
            if (!result.equals("Email sent")) {
                throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
            }
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
    public void deleteCredit(Integer id) {
        Credit credit = creditRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Credit with id " + id + " not found")
        );
        creditRepository.deleteById(id);
    }

    @Override
    public List<Credit> findCreditsByUserId(Integer userId) {
        return creditRepository.findCreditsByUserId(userId);
    }

    @Override
    public Double getTotalMontantDemande() {
        return creditRepository.getTotalMontantDemande();
    }


//    @Override
//    @Transactional
//    public Credit delegateCredit(Integer creditId, Double montant) {
//        Credit credit = creditRepository.findById(creditId).orElseThrow(() -> new RuntimeException("Credit not found"));
//
//
//        Budget budget = credit.getBudget();
//        if (budget.getBudget() >= montant) {
//            budget.setBudget(budget.getBudget() - montant);
//        } else {
//            throw new RuntimeException("Insufficient budget for " + budget.getPrevisionOperation());
//        }
//        credit.setMontant(montant);
//        credit.setEtat("Délégué");
//        credit.setDateDelegation(LocalDate.now());
//
//        budgetRepository.save(budget);
//        return creditRepository.save(credit);
//    }



}
