package com.example.mySpringProject.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity

@Table(name= "credits")
public class Credit {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;
    private String natureCredit;
    private String detail;
    private Double montant;

    private LocalDate dateDemande;
    private LocalDate dateDelegation;
    private String etat;
    private String emailDPDPM;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idFeu")
    private Feu feu;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idBudget")
    private Budget budget;

    public Credit(Integer id,String emailDPDPM, String natureCredit, String detail, Double montant, LocalDate dateDemande, LocalDate dateDelegation, String etat, Feu feu, Budget budget) {
        this.id = id;
        this.emailDPDPM = emailDPDPM;
        this.natureCredit = natureCredit;
        this.detail = detail;
        this.montant = montant;
        this.dateDemande = dateDemande;
        this.dateDelegation = dateDelegation;
        this.etat = etat;
        this.feu = feu;
        this.budget = budget;
    }

    public Credit() {

    }
}
