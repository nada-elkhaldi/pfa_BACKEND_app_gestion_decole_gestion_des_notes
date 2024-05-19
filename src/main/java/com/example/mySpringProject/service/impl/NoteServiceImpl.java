package com.example.mySpringProject.service.impl;


import com.example.mySpringProject.dto.MatiereDto;
import com.example.mySpringProject.dto.NoteDto;
import com.example.mySpringProject.mapper.MatiereMapper;
import com.example.mySpringProject.mapper.NoteMapper;
import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.Note;
import com.example.mySpringProject.model.Semestre;
import com.example.mySpringProject.repository.MatiereRepository;
import com.example.mySpringProject.repository.NoteRepository;
import com.example.mySpringProject.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;
    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    @Override
    public List<NoteDto> addNote(List<NoteDto> noteDto) {
        List<Note> savedNotes = new ArrayList<>();
        for (NoteDto request : noteDto) {
            Note note = NoteMapper.mapToNote(request);
            Note savedNote = noteRepository.save(note);
            savedNotes.add(savedNote);
        }
        List<NoteDto> savedNotesDto = NoteMapper.mapToNoteDtoList(savedNotes);
        return savedNotesDto;
    }

    @Override
    public List<Note> getNotesBySemestreAndMatiere(Semestre semestre, Matiere matiere) {
        return noteRepository.findBySemestreAndMatiere(semestre, matiere);
    }


    @Override
    public Double calculerMoyenne(Note note) {
        Double moyenne = (note.getNoteTp() * note.getCoefTp() +
                note.getNoteExam() * note.getCoefExam() +
                note.getNoteCtl1() * note.getCoefCtl1() +
                note.getNoteCtl2() * note.getCoefCtl2()) /
                (note.getCoefTp() + note.getCoefExam() + note.getCoefCtl1() + note.getCoefCtl2());
        note.setMoyenne(moyenne);
        noteRepository.save(note);
        return moyenne;
    }
}
