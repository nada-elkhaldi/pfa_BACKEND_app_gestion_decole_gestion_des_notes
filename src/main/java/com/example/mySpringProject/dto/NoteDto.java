package com.example.mySpringProject.dto;

import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.Semestre;
import com.example.mySpringProject.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class NoteDto {

    private Integer id;
    private Double noteTp;
    private Double coefTp;
    private Double noteExam;
    private Double coefExam;
    private Double noteCtl1;
    private Double coefCtl1;
    private Double noteCtl2;
    private Double coefCtl2;
    private Double moyenne;

    private User etudiant;
    private Matiere matiere;
    private Semestre semestre;

    public NoteDto() {
    }


    public NoteDto(Integer id, Double noteTp, Double coefTp, Double noteExam, Double coefExam, Double noteCtl1, Double coefCtl1, Double noteCtl2, Double coefCtl2,Double moyenne, User etudiant, Matiere matiere, Semestre semestre) {
        this.id = id;
        this.noteTp = noteTp;
        this.coefTp = coefTp;
        this.noteExam = noteExam;
        this.coefExam = coefExam;
        this.noteCtl1 = noteCtl1;
        this.coefCtl1 = coefCtl1;
        this.noteCtl2 = noteCtl2;
        this.coefCtl2 = coefCtl2;
        this.moyenne = moyenne;
        this.etudiant = etudiant;
        this.matiere = matiere;
        this.semestre = semestre;
    }
}
