package com.example.mySpringProject.dto;


import com.example.mySpringProject.model.Groupe;
import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClasseDto {

    private Integer id;
    private String nom;
    private String specialite;
    private String annee;
    private Integer nbrGroupe;
    private Matiere matiere;

    public ClasseDto(Integer id, String nom, String specialite, String annee, Integer nbrCroupe, List<User> etudiants, List<Groupe> groupes, Matiere matiere) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
        this.annee = annee;
        this.nbrGroupe = nbrCroupe;
        this.matiere = matiere;
    }



}
