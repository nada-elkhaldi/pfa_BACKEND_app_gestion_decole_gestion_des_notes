package com.example.mySpringProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "semestres")
public class Semestre {
    @Id
    @GeneratedValue
    private Integer id;
    private String semestreAnnee;


    public Semestre(Integer id, String semestreAnnee) {
        this.id = id;
        this.semestreAnnee = semestreAnnee;

    }

    public Semestre() {
    }

}