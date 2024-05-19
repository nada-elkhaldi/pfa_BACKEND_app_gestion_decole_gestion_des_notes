package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.Note;
import com.example.mySpringProject.model.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query("SELECT n FROM Note n WHERE n.semestre = :semestre AND n.matiere = :matiere")
    List<Note> findBySemestreAndMatiere(@Param("semestre") Semestre semestre, @Param("matiere") Matiere matiere);
}
