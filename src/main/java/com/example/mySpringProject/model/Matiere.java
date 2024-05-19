package com.example.mySpringProject.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Setter
@Getter
@Entity
@Table(name = "matieres")
public class Matiere {
    @Id
    @GeneratedValue
    private Integer id;
    private String code;
    private String intitule;

    @OneToMany(mappedBy = "matiere")
    private List<User> enseignants;

    @OneToMany(mappedBy = "matiere")
    private List<Groupe> groupes;

    @OneToMany(mappedBy = "matiere")
    private List<Classe> classes;

    @OneToMany(mappedBy = "matiere")
    private List<Note> notes;

    @ManyToOne(optional = true)
    @JoinColumn(name = "semestre_id")
    private Semestre semestre;

    

    public Matiere() {

    }



    public Matiere(Integer id, String code, String intitule, List<User> enseignants, List<Groupe> groupes, List<Classe> classes, Semestre semestre, List<Note> notes) {
        this.id = id;
        this.code = code;
        this.intitule = intitule;
        this.enseignants = enseignants;
        this.groupes = groupes;
        this.classes = classes;
        this.semestre = semestre;
        this.notes = notes;
    }
}
