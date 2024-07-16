package com.example.mySpringProject.dto;



import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter


public class CreditDto {

    private Integer id;
    private String emailDPDPM;
    private String natureCredit;
    private String detail;
    private Double montant;
    private LocalDate dateDemande;
    private LocalDate dateDelegation;
    private String etat;
    private Integer idFeu;
   private Integer idBudget;

    public CreditDto(Integer id, String emailDPDPM ,String natureCredit, String detail, Double montant, LocalDate dateDemande, LocalDate dateDelegation, String etat, Integer idFeu, Integer idBudget) {
        this.id = id;
        this.emailDPDPM = emailDPDPM;
        this.natureCredit = natureCredit;
        this.detail = detail;
        this.montant = montant;
        this.dateDemande = dateDemande;
        this.dateDelegation = dateDelegation;
        this.etat = etat;
        this.idFeu = idFeu;
        this.idBudget = idBudget;
    }
}
