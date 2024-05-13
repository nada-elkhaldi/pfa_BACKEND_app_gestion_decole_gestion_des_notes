package com.example.mySpringProject.mapper;


import com.example.mySpringProject.dto.ClasseDto;
import com.example.mySpringProject.model.Classe;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ClasseMapper {

    public static ClasseDto mapToClasseDto(Classe classe) {
        return new ClasseDto(
                classe.getId(),
                classe.getNom(),
                classe.getSpecialite(),
                classe.getAnnee(),
                classe.getNbrGroupe(),
                classe.getEtudiants(),
                classe.getGroupes(),
                classe.getMatiere()
        );

    }

    public static Classe mapToClasse(ClasseDto classeDto) {
        return new Classe(
                classeDto.getId(),
                classeDto.getNom(),
                classeDto.getSpecialite(),
                classeDto.getAnnee(),
                classeDto.getNbrGroupe(),
                classeDto.getMatiere()

        );
    }

    public static List<ClasseDto> mapToClasseDtoList(List<Classe> classes) {
        List<ClasseDto> classeDtoList = new ArrayList<>();
        for (Classe classe : classes) {
            ClasseDto classeDto = mapToClasseDto(classe);
            classeDtoList.add(classeDto);
        }
        return classeDtoList;
    }
}
