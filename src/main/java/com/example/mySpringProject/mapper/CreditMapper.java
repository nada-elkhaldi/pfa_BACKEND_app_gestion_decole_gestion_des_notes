package com.example.mySpringProject.mapper;


import com.example.mySpringProject.dto.CreditDto;
import com.example.mySpringProject.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CreditMapper {

    public static CreditDto mapToCreditDto(Credit credit) {
        return new CreditDto(
                credit.getId(),
                credit.getMontantDemande(),
                credit.getDateDemande(),
                credit.getEtat(),
                credit.getMotif(),
                credit.getFeu() != null ? credit.getFeu().getId() : null,
                credit.getDemandeur() != null ? credit.getDemandeur().getId() : null,
                credit.getPanne() != null ? credit.getPanne().getId() : null,
                credit.getFeu(),
                credit.getDemandeur(),
                credit.getPanne()

        );
    }

    public static Credit mapToCredit(CreditDto creditDto) {
        Credit credit= new Credit();
        credit.setId(creditDto.getId());
        credit.setMontantDemande(creditDto.getMontantDemande());
        credit.setDateDemande(LocalDate.now());
         credit.setEtat("Pas Délégué");
         credit.setMotif(creditDto.getMotif());
        if (creditDto.getIdFeu() != null) {
            Feu feu = new Feu();
            feu.setId(creditDto.getIdFeu());
            credit.setFeu(feu);
        }


        if (creditDto.getIdDemandeur() != null) {
            User demandeur = new User();
            demandeur.setId(creditDto.getIdDemandeur());
            credit.setDemandeur(demandeur);
        }


        if (creditDto.getIdPanne() != null) {
            Panne panne = new Panne();
            panne.setId(creditDto.getIdPanne());
            credit.setPanne(panne);
        }
        return credit;

    }
}
