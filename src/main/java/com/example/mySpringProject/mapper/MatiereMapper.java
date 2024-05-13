package com.example.mySpringProject.mapper;


import com.example.mySpringProject.dto.MatiereDto;
import com.example.mySpringProject.model.Matiere;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class MatiereMapper {

    public static MatiereDto mapToMatiereDto(Matiere matiere) {
        return new MatiereDto(
                matiere.getId(),
                matiere.getCode(),
                matiere.getIntitule(),
                matiere.getEnseignants(),
                matiere.getClasses(),
                matiere.getGroupes(),
                matiere.getSemestre()
        );

    }

    public static Matiere mapToMatiere(MatiereDto matiereDto) {
        return new Matiere(
                matiereDto.getId(),
                matiereDto.getCode(),
                matiereDto.getIntitule(),
                matiereDto.getEnseignants(),
                matiereDto.getGroupes(),
                matiereDto.getClasses(),
                matiereDto.getSemestre()

        );
    }

    public static List<MatiereDto> mapToMatiereDtoList(List<Matiere> matieres) {
        List<MatiereDto> matiereDtoList = new ArrayList<>();
        for (Matiere matiere : matieres) {
            MatiereDto matiereDto = mapToMatiereDto(matiere);
            matiereDtoList.add(matiereDto);
        }
        return matiereDtoList;
    }
}
