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

    @OneToMany(mappedBy = "semestre")
    private List<Matiere> matieres;

    @OneToMany(mappedBy = "semestre")
    private List<Note> notes;

    public Semestre(Integer id, String semestreAnnee, List<Matiere> matieres, List<Note> notes) {
        this.id = id;
        this.semestreAnnee = semestreAnnee;
        this.matieres = matieres;
        this.notes = notes;
    }

    public Semestre() {
    }

    public Semestre(Integer id, String semestreAnnee) {
    }
}