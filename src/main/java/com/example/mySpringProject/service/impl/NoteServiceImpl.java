package com.example.mySpringProject.service.impl;



import com.example.mySpringProject.dto.NoteDto;
import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.Note;
import com.example.mySpringProject.model.Semestre;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.repository.NoteRepository;
import com.example.mySpringProject.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;
    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    //@Override
//    public List<NoteDto> addNote(List<NoteDto> noteDtoList) {
//        List<Note> notesToSave = noteDtoList.stream()
//                .map(NoteMapper::mapToNote)
//                .collect(Collectors.toList());
//
//        List<Note> savedNotes = noteRepository.saveAll(notesToSave);
//
//        return savedNotes.stream()
//                .map(NoteMapper::mapToNoteDto)
//                .collect(Collectors.toList());
//    }
    @Override
    public List<NoteDto> addNoteWithAverageCalculation(List<NoteDto> noteDtoList) {
        List<NoteDto> notesToSave = new ArrayList<>();

        for (NoteDto noteDto : noteDtoList) {
            // Calculer la moyenne
            Double moyenne = calculateAverage(noteDto);

            // Mapper NoteDto à Note pour sauvegarde
            Note note = mapToNoteEntity(noteDto, moyenne);

            // Sauvegarder la note
            note = noteRepository.save(note);

            // Mapper la note sauvegardée à NoteDto pour retourner
            NoteDto savedNoteDto = mapToNoteDto(note);
            notesToSave.add(savedNoteDto);

            // Ajouter l'ID de la note sauvegardée à NoteDto
            savedNoteDto.setId(note.getId()); // Assurez-vous que NoteDto a une propriété pour l'ID
        }

        return notesToSave;
    }


    private Note mapToNoteEntity(NoteDto noteDto, Double moyenne) {
        // Mapper NoteDto à Note
        Note note = new Note();

        // Exemple avec Semestre
        Semestre semestre = new Semestre();
        semestre.setId(noteDto.getSemestreId()); // Assurez-vous que setId prend un Integer
        note.setSemestre(semestre); // Utilisation de l'entité Semestre

        // Faites de même pour Matiere et User
        Matiere matiere = new Matiere();
        matiere.setId(noteDto.getMatiereId());
        note.setMatiere(matiere);

        User etudiant = new User(); // Vous devez ajuster selon votre classe User
        etudiant.setId(noteDto.getEtudiantId());
        note.setEtudiant(etudiant);

        // Ajustez les autres attributs
        note.setNoteTp(noteDto.getNoteTp());
        note.setCoefTp(noteDto.getCoefTp());
        note.setNoteExam(noteDto.getNoteExam());
        note.setCoefExam(noteDto.getCoefExam());
        note.setNoteCtl1(noteDto.getNoteCtl1());
        note.setCoefCtl1(noteDto.getCoefCtl1());
        note.setNoteCtl2(noteDto.getNoteCtl2());
        note.setCoefCtl2(noteDto.getCoefCtl2());
        note.setMoyenne(moyenne);

        return note;
    }


    private NoteDto mapToNoteDto(Note note) {

        NoteDto noteDto = new NoteDto();

        noteDto.setSemestreId(note.getSemestre().getId());

        noteDto.setMatiereId(note.getMatiere().getId());
        noteDto.setEtudiantId(note.getEtudiant().getId());

        noteDto.setNoteTp(note.getNoteTp());
        noteDto.setCoefTp(note.getCoefTp());
        noteDto.setNoteExam(note.getNoteExam());
        noteDto.setCoefExam(note.getCoefExam());
        noteDto.setNoteCtl1(note.getNoteCtl1());
        noteDto.setCoefCtl1(note.getCoefCtl1());
        noteDto.setNoteCtl2(note.getNoteCtl2());
        noteDto.setCoefCtl2(note.getCoefCtl2());
        noteDto.setMoyenne(note.getMoyenne());

        return noteDto;
    }


    private Double calculateAverage(NoteDto noteDto) {
        return (noteDto.getNoteTp() * noteDto.getCoefTp() +
                noteDto.getNoteExam() * noteDto.getCoefExam() +
                noteDto.getNoteCtl1() * noteDto.getCoefCtl1() +
                noteDto.getNoteCtl2() * noteDto.getCoefCtl2()) /
                (noteDto.getCoefTp() + noteDto.getCoefExam() + noteDto.getCoefCtl1() + noteDto.getCoefCtl2());
    }

    @Override
    public List<Note> getNotesBySemestreAndMatiere(Semestre semestre, Matiere matiere) {
        return noteRepository.findBySemestreAndMatiere(semestre, matiere);
    }

    @Override
    public List<Note> findBySemesterIdAndStudentId(Integer semesterId, Integer studentId) {
        return noteRepository.findBySemesterIdAndStudentId(semesterId, studentId);
    }


//    @Override
//    public Double calculerMoyenne(Note note) {
//        Double moyenne = (note.getNoteTp() * note.getCoefTp() +
//                note.getNoteExam() * note.getCoefExam() +
//                note.getNoteCtl1() * note.getCoefCtl1() +
//                note.getNoteCtl2() * note.getCoefCtl2()) /
//                (note.getCoefTp() + note.getCoefExam() + note.getCoefCtl1() + note.getCoefCtl2());
//        note.setMoyenne(moyenne);
//        noteRepository.save(note);
//        return moyenne;
//    }
}
