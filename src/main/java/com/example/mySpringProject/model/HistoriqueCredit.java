package com.example.mySpringProject.model;




import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity

@Table(name= "credits")
public class HistoriqueCredit {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    private Double creditDelegue;
    private LocalDate dateDelegation;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idDemande")
    private Credit creditDemande;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idBudget")
    private Budget budget;

}
