package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClasseRepository extends JpaRepository<Classe, Integer> {
}
