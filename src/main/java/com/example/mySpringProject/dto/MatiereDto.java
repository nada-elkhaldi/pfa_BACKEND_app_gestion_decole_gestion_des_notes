package com.example.mySpringProject.dto;


import com.example.mySpringProject.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class MatiereDto {
    private Integer id;
    private String code;
    private String intitule;
    private List<User> enseignants;
    private List<Classe> classes;
   private List<Groupe> groupes;
   private Semestre semestre;

    public MatiereDto(Integer id, String code, String intitule, List<User> enseignants, List<Classe> classes, List<Groupe> groupes, Semestre semestre) {
        this.id = id;
        this.code = code;
        this.intitule = intitule;
        this.enseignants = enseignants;
        this.classes = classes;
        this.groupes = groupes;
        this.semestre = semestre;
    }
}
