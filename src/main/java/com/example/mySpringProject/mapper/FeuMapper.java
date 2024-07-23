package com.example.mySpringProject.mapper;

import com.example.mySpringProject.dto.FeuDto;
import com.example.mySpringProject.model.Feu;
import org.springframework.stereotype.Component;

@Component
public class FeuMapper {

    public static FeuDto mapToFeuDto(Feu feu) {
        return new FeuDto(
                feu.getId(),
                feu.getNumero(),
                feu.getNomLocalisation(),
                feu.getPosition(),
                feu.getCaracteristiques(),
                feu.getElevation(),
                feu.getPortee(),
                feu.getDescription(),
                feu.getInfos(),
                feu.getEtatFonctionnement(),
                feu.getPort(),
                feu.getZone()
        );
    }

    public static Feu mapToFeu(FeuDto feuDto) {
        return new Feu(
                feuDto.getId(),
                feuDto.getNumero(),
                feuDto.getNomLocalisation(),
                feuDto.getPosition(),
                feuDto.getCaracteristiques(),
                feuDto.getElevation(),
                feuDto.getPortee(),
                feuDto.getDescription(),
                feuDto.getInfos(),
                feuDto.getEtatFonctionnement(),
                feuDto.getPort(),
                feuDto.getZone()

        );
    }
}
