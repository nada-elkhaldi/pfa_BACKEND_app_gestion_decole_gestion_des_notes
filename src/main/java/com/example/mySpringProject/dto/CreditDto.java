package com.example.mySpringProject.dto;



import com.example.mySpringProject.model.Feu;
import com.example.mySpringProject.model.Panne;
import com.example.mySpringProject.model.User;
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
    private String motif;
    private Integer idFeu;
    private Integer idDemandeur;
    private Integer idPanne;
    private Feu feu;
    private User demandeur;
    private Panne panne;


    public CreditDto(Integer id, Double montant, LocalDate dateDemande, String etat,String motif, Integer idFeu, Integer idDemandeur, Integer idPanne, Feu feu, User demandeur, Panne panne) {
        this.id = id;
        this.montantDemande = montant;
        this.dateDemande = dateDemande;
        this.etat = etat;
        this.motif=motif;
        this.idFeu = idFeu;
        this.idDemandeur = idDemandeur;
        this.idPanne = idPanne;
        this.feu = feu;
        this.demandeur = demandeur;
        this.panne = panne;

    }
}
