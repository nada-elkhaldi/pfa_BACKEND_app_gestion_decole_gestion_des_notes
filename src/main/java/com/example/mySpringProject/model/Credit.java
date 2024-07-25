package com.example.mySpringProject.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity

@Table(name= "demande_credits")
public class Credit {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    private Double montantDemande;

    private LocalDate dateDemande;
    private String etat;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idFeu")
    private Feu feu;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idUser")
    private User demandeur;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idPanne")
    private Panne panne;


    public Credit(Integer id,  LocalDate dateDemande, Double montantDemande, String etat, Feu feu, User user, Panne panne) {
        this.id = id;
        this.dateDemande = dateDemande;
        this.montantDemande = montantDemande;
        this.etat = etat;
        this.feu = feu;
        this.demandeur = user;
        this.panne = panne;

    }

    public Credit() {

    }
}
