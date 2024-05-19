package com.example.mySpringProject.mapper;

import com.example.mySpringProject.dto.MatiereDto;
import com.example.mySpringProject.dto.NoteDto;
import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.Note;

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
                note.getEtudiant(),
                note.getMatiere(),
                note.getSemestre()
        );

    }
    public static Note mapToNote(NoteDto noteDto) {
        return new Note(
                noteDto.getId(),
                noteDto.getNoteTp(),
                noteDto.getCoefTp(),
                noteDto.getNoteExam(),
                noteDto.getCoefExam(),
                noteDto.getNoteCtl1(),
                noteDto.getCoefCtl1(),
                noteDto.getNoteCtl2(),
                noteDto.getCoefCtl2(),
                noteDto.getMoyenne(),
                noteDto.getEtudiant(),
                noteDto.getMatiere(),
                noteDto.getSemestre()

        );
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
