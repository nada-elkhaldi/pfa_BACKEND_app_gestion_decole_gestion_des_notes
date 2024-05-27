package com.example.mySpringProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "groupes")
public class Groupe {
    @Id
    @GeneratedValue
    private Integer id;
    private String groupName;

    public Groupe(Integer id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public Groupe() {
    }

    @ManyToOne(optional = true)
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;




    public Groupe(Integer id, String groupName, Matiere matiere) {
        this.id = id;
        this.groupName = groupName;
        this.matiere = matiere;

    }

}