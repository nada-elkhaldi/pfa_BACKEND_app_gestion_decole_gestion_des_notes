package com.example.mySpringProject.dto;



import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter


public class CreditDto {

    private Integer id;
    private Double montantDemande;
    private LocalDate dateDemande;
    private String etat;
    private Integer idFeu;
    private Integer idDemandeur;
    private Integer idPanne;


    public CreditDto(Integer id, Double montant, LocalDate dateDemande, String etat, Integer idFeu, Integer idDemandeur, Integer idPanne) {
        this.id = id;
        this.montantDemande = montant;
        this.dateDemande = dateDemande;
        this.etat = etat;
        this.idFeu = idFeu;
        this.idDemandeur = idDemandeur;
        this.idPanne = idPanne;

    }
}
