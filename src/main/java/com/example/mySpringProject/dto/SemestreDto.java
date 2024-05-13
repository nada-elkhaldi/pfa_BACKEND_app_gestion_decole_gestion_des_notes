package com.example.mySpringProject.dto;


import com.example.mySpringProject.model.Matiere;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SemestreDto {

    private Integer id;
    private String semestreAnnee;
    private List<Matiere> matieres;

    public SemestreDto(Integer id, String semestreAnnee, List<Matiere> matieres) {
        this.id = id;
        this.semestreAnnee = semestreAnnee;
        this.matieres=matieres;
    }
}