package com.example.mySpringProject.dto;


import com.example.mySpringProject.model.Groupe;
import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClasseDto {

    private Integer id;
    private String nom;
    private String specialite;
    private String annee;
    private Integer nbrGroupe;
    private Matiere matiere;





    public ClasseDto(Integer id, String nom, String specialite, String annee, Integer nbrGroupe, Matiere matiere) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
        this.annee = annee;
        this.nbrGroupe = nbrGroupe;
        this.matiere = matiere;

    }


}
