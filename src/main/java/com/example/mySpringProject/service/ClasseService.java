package com.example.mySpringProject.service;

import com.example.mySpringProject.dto.ClasseDto;
import com.example.mySpringProject.model.Classe;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface ClasseService {

    List<ClasseDto> createClasse(List<ClasseDto> classeDto);
    List<ClasseDto> getAllClasses();
    Classe getClasseById(int id);

}
