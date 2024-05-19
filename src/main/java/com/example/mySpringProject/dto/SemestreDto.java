package com.example.mySpringProject.dto;


import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.Note;
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
    private List<Note> notes;

    public SemestreDto(Integer id, String semestreAnnee, List<Matiere> matieres, List<Note> notes) {
        this.id = id;
        this.semestreAnnee = semestreAnnee;
        this.matieres=matieres;
        this.notes = notes;
    }
}