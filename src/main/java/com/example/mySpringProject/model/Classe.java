package com.example.mySpringProject.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Setter
@Getter
@Entity
@Table(name = "classes")
public class Classe {
    @Id
    @GeneratedValue
    private Integer id;
    private String nom;
    private String specialite;
    private String annee;
    private Integer nbrGroupe;


    @OneToMany(mappedBy = "classe")
    private List<User> etudiants;

    @OneToMany(mappedBy = "classe")
    private List<Groupe> groupes;

    @ManyToOne(optional = true)
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    public Classe(Integer id, String nom, String specialite, String annee, Integer nbrCroupe, Matiere matiere) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
        this.annee = annee;
        this.nbrGroupe = nbrCroupe;
        this.matiere = matiere;
    }

    public Classe() {

    }
}
