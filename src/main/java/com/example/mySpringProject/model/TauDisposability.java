package com.example.mySpringProject.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "taux_disponibilites")
public class TauDisposability {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "feu_id")
    private Feu feu;

    private double tauxCalcule;
    private LocalDate startDate;
    private LocalDate endDate;
    private double outOfServiceTime;


}

