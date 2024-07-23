package com.example.mySpringProject.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "interventions")
public class Intervention {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    private String intervenant;
    private LocalDate dateIntervention;
    private Double cout;

    private String rapportIntervention;
    @ManyToOne
    @JoinColumn(name = "id_panne")
    private Panne panne;

}
