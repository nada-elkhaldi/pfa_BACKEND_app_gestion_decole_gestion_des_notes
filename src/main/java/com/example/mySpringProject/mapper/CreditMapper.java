package com.example.mySpringProject.mapper;


import com.example.mySpringProject.dto.CreditDto;
import com.example.mySpringProject.model.Budget;
import com.example.mySpringProject.model.Credit;
import com.example.mySpringProject.model.Feu;
import org.springframework.stereotype.Component;

@Component
public class CreditMapper {

    public static CreditDto mapToCreditDto(Credit credit) {
        return new CreditDto(
                credit.getId(),
                credit.getEmailDPDPM(),
                credit.getNatureCredit(),
                credit.getDetail(),
                credit.getMontant(),
                credit.getDateDemande(),
                credit.getDateDelegation(),
                credit.getEtat(),
                credit.getFeu() != null ? credit.getFeu().getId() : null,
                credit.getBudget() != null ? credit.getBudget().getId() : null

        );
    }

    public static Credit mapToCredit(CreditDto creditDto) {
        Credit credit= new Credit();
        credit.setId(creditDto.getId());
        credit.setEmailDPDPM(creditDto.getEmailDPDPM());
        credit.setNatureCredit(creditDto.getNatureCredit());
        credit.setDetail(creditDto.getDetail());
        credit.setMontant(creditDto.getMontant());
        credit.setDateDemande(creditDto.getDateDemande());
        credit.setDateDelegation(creditDto.getDateDelegation());
        credit.setEtat(creditDto.getEtat());
        if (creditDto.getIdFeu() != null) {
            Feu feu = new Feu();
            feu.setId(creditDto.getIdFeu());
            credit.setFeu(feu);
        }

        if (creditDto.getIdBudget() != null) {
            Budget budget = new Budget();
            budget.setId(creditDto.getIdBudget());
            credit.setBudget(budget);
        }


        return credit;

    }
}
