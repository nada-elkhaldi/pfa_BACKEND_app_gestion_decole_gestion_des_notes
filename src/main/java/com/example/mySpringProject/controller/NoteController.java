package com.example.mySpringProject.controller;


import com.example.mySpringProject.dto.NoteDto;
import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.Note;
import com.example.mySpringProject.model.Semestre;
import com.example.mySpringProject.repository.MatiereRepository;
import com.example.mySpringProject.repository.NoteRepository;
import com.example.mySpringProject.repository.SemestreRepository;
import com.example.mySpringProject.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value= "api/v1/notes")
public class NoteController {

    private NoteService noteService;
    private SemestreRepository semestreRepository;
    private MatiereRepository matiereRepository;
    private NoteRepository noteRepository;

    @Autowired
    public NoteController(NoteService noteService,SemestreRepository semestreRepository , MatiereRepository matiereRepository, NoteRepository noteRepository ) {
        this.noteService = noteService;
        this.semestreRepository = semestreRepository;
        this.matiereRepository = matiereRepository;
        this.noteRepository = noteRepository;
    }


    @PostMapping
    public ResponseEntity<List<NoteDto>> addNote(@RequestBody List<NoteDto> noteDto) {
        List<NoteDto> savedNotes = noteService.addNote(noteDto);
        return ResponseEntity.ok(savedNotes);
    }
    @GetMapping("/{semestreId}/{matiereId}")
    public List<Note> getNotesBySemestreAndMatiere(
            @PathVariable("semestreId") Integer semestreId,
            @PathVariable("matiereId") Integer matiereId) {
        try {
            Semestre semestre = semestreRepository.findById(semestreId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semestre not found"));
            Matiere matiere = matiereRepository.findById(matiereId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matiere not found"));
            return noteService.getNotesBySemestreAndMatiere(semestre, matiere);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", ex);
        }
    }

    @GetMapping("/moyenne/{noteId}")
    public Double calculerMoyenne(@PathVariable("noteId") Integer noteId) throws ChangeSetPersister.NotFoundException {

        Note note = noteRepository.findById(noteId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return noteService.calculerMoyenne(note);
    }
}
