package com.example.mySpringProject.mapper;


import com.example.mySpringProject.dto.SemestreDto;
import com.example.mySpringProject.model.Semestre;
import org.springframework.stereotype.Component;


@Component
public class SemestreMapper {

    public static SemestreDto mapToSemestreDto(Semestre semestre) {
        return new SemestreDto(
                semestre.getId(),
                semestre.getSemestreAnnee(),
                semestre.getMatieres()
        );

    }

    public static Semestre mapToSemestre(SemestreDto semestreDto) {
        return new Semestre(
                semestreDto.getId(),
                semestreDto.getSemestreAnnee(),
                semestreDto.getMatieres()
        );
    }
}
