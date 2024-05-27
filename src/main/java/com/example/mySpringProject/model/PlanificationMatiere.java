package com.example.mySpringProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name = "planification_matiere")
public class PlanificationMatiere {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "enseignant_id")
    private User enseignant;

    @ManyToOne(optional = true)
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    @ManyToOne(optional = true)
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne(optional = true)
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;

    @ManyToOne(optional = true)
    @JoinColumn(name = "semestre_id")
    private Semestre semestre;



    public PlanificationMatiere(Integer id, User enseignant,Matiere matiere, Classe classe, Groupe groupe, Semestre semestre) {
        this.id = id;
        this.enseignant = enseignant;
        this.matiere = matiere;
        this.classe = classe;
        this.groupe = groupe;
        this.semestre = semestre;
    }

    public PlanificationMatiere() {

    }
}
