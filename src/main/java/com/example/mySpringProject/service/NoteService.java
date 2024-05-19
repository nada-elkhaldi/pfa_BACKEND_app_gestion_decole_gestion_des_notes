package com.example.mySpringProject.service;

import com.example.mySpringProject.dto.NoteDto;
import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.Note;
import com.example.mySpringProject.model.Semestre;

import java.util.List;

public interface NoteService {

    List<NoteDto> addNote(List<NoteDto> noteDto);
    List<Note> getNotesBySemestreAndMatiere(Semestre semestre, Matiere matiere);
    Double calculerMoyenne(Note note);
}
