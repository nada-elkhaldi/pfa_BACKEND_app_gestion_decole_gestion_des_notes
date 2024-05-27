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

//    @OneToMany(mappedBy = "matiere")
//    private List<Note> notes;
    

    public Matiere() {

    }



    public Matiere(Integer id, String code, String intitule) {
        this.id = id;
        this.code = code;
        this.intitule = intitule;
    }
}
