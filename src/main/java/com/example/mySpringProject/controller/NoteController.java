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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.mySpringProject.mapper.NoteMapper.mapToNoteDto;

@RequestMapping(value = "/api5/notes", method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
@RestController
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

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/addNote")
    public ResponseEntity<List<NoteDto>> addNoteWithAverageCalculation(@RequestBody List<NoteDto> noteDtoList) {
        List<NoteDto> savedNotes = noteService.addNoteWithAverageCalculation(noteDtoList);
        return ResponseEntity.ok(savedNotes);
    }



    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable("noteId") Integer noteId) {
        try {
            Optional<Note> note = noteRepository.findById(noteId);
            if (note.isPresent()) {
                return ResponseEntity.ok(note.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération de la note", ex);
        }
    }

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@GetMapping("/{semestreId}/{matiereId}")
public List<NoteDto> getNotesBySemestreAndMatiere(
        @PathVariable("semestreId") Integer semestreId,
        @PathVariable("matiereId") Integer matiereId) {
    try {
        Semestre semestre = semestreRepository.findById(semestreId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semestre not found"));
        Matiere matiere = matiereRepository.findById(matiereId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matiere not found"));

        List<Note> notes = noteService.getNotesBySemestreAndMatiere(semestre, matiere);
        List<NoteDto> noteDtos = new ArrayList<>();

        for (Note note : notes) {
            NoteDto noteDto = mapToNoteDto(note);
            noteDtos.add(noteDto);
        }
        return noteDtos;
    } catch (ResponseStatusException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", ex);
    }
}
//pour recuperer les notes d etudiant
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/etudiant/{semesterId}/{studentId}")
    public ResponseEntity<List<Note>> getStudentNotesBySemester(
            @PathVariable Integer semesterId, @PathVariable Integer studentId) {
        List<Note> studentNotes = noteService.findBySemesterIdAndStudentId(semesterId, studentId);
        return ResponseEntity.ok(studentNotes);
    }
}
