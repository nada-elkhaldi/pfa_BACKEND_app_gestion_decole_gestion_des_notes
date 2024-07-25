package com.example.mySpringProject.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "planification_interventions")
public class PlanificationIntervention {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    private String descriptif;
    private LocalDate echancier;

    @ManyToOne
    @JoinColumn(name = "id_panne")
    private Panne panne;

}
