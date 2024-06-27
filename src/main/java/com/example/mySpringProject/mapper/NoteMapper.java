package com.example.mySpringProject.mapper;

import com.example.mySpringProject.dto.MatiereDto;
import com.example.mySpringProject.dto.NoteDto;
import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.Note;
import com.example.mySpringProject.model.Semestre;
import com.example.mySpringProject.model.User;

import java.util.ArrayList;
import java.util.List;

public class NoteMapper {
    public static NoteDto mapToNoteDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getNoteTp(),
                note.getCoefTp(),
                note.getNoteExam(),
                note.getCoefExam(),
                note.getNoteCtl1(),
                note.getCoefCtl1(),
                note.getNoteCtl2(),
                note.getCoefCtl2(),
                note.getMoyenne(),
                note.getEtudiant() != null ? note.getEtudiant().getId() : null,
                note.getMatiere() != null ? note.getMatiere().getId() : null,
                note.getSemestre() != null ? note.getSemestre().getId() : null
        );
    }
    public static Note mapToNote(NoteDto noteDto) {
        Note note = new Note();
        note.setId(noteDto.getId());
        note.setNoteTp(noteDto.getNoteTp());
        note.setCoefTp(noteDto.getCoefTp());
        note.setNoteExam(noteDto.getNoteExam());
        note.setCoefExam(noteDto.getCoefExam());
        note.setNoteCtl1(noteDto.getNoteCtl1());
        note.setCoefCtl1(noteDto.getCoefCtl1());
        note.setNoteCtl2(noteDto.getNoteCtl2());
        note.setCoefCtl2(noteDto.getCoefCtl2());
        note.setMoyenne(noteDto.getMoyenne());
        // Set User, Matiere, Semestre using their IDs
        if (noteDto.getEtudiantId() != null) {
            User etudiant = new User();
            etudiant.setId(noteDto.getEtudiantId());
            note.setEtudiant(etudiant);
        }

        if (noteDto.getMatiereId() != null) {
            Matiere matiere = new Matiere();
            matiere.setId(noteDto.getMatiereId());
            note.setMatiere(matiere);
        }

        if (noteDto.getSemestreId() != null) {
            Semestre semestre = new Semestre();
            semestre.setId(noteDto.getSemestreId());
            note.setSemestre(semestre);
        }

        return note;
    }

    public static List<NoteDto> mapToNoteDtoList(List<Note> notes) {
        List<NoteDto> noteDtoList = new ArrayList<>();
        for (Note note : notes) {
            NoteDto noteDto = mapToNoteDto(note);
            noteDtoList.add(noteDto);
        }
        return noteDtoList;
    }
}
