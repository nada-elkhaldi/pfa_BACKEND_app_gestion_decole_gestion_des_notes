package com.example.mySpringProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity

@Table(name= "notes")
public class Note {


    @Id
    @GeneratedValue
    private Integer id;
    private Double noteTp;
    private Double coefTp;
    private Double noteExam;
    private Double coefExam;
    private Double noteCtl1;
    private Double coefCtl1;
    private Double noteCtl2;
    private Double coefCtl2;

    private Double moyenne;

    @ManyToOne(optional = true)
    @JoinColumn(name = "etudiant_id")
    private User etudiant;

    @ManyToOne(optional = true)
    @JoinColumn(name = "semestre_id")
    private Semestre semestre;

    @ManyToOne(optional = true)
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;


    public Note() {

    }

    public Note(Integer id, Double noteTp, Double coefTp, Double noteExam, Double coefExam, Double noteCtl1, Double coefCtl1, Double noteCtl2, Double coefCtl2,Double moyenne, User etudiant, Matiere matiere, Semestre semestre) {
        this.id = id;
        this.noteTp = noteTp;
        this.coefTp = coefTp;
        this.noteExam = noteExam;
        this.coefExam = coefExam;
        this.noteCtl1 = noteCtl1;
        this.coefCtl1 = coefCtl1;
        this.noteCtl2 = noteCtl2;
        this.coefCtl2 = coefCtl2;
        this.moyenne = 0.0;
        this.etudiant = etudiant;
        this.matiere = matiere;
        this.semestre = semestre;
    }

    public User getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(User etudiant) {
        this.etudiant = etudiant;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }



}
