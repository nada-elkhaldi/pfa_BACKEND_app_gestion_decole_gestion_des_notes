package com.example.mySpringProject.mapper;


import com.example.mySpringProject.dto.PanneDto;
import com.example.mySpringProject.model.*;
import org.springframework.stereotype.Component;

@Component
public class PanneMapper {


    public static PanneDto mapToPanDto(Panne panne) {
        return new PanneDto(
                panne.getId(),
                panne.getNatureDePanne(),
                panne.getDatePanne(),
                panne.getEtatGeneral(),
                panne.getEtatFonctionnementDeFeuDeSecours(),
                panne.getMotifDePanne(),
                panne.getPlanDAction(),
                panne.getDateRemiseEnService(),
                panne.getPrevisionDeResolution(),
                panne.getOutOfServiceTime(),
                panne.getDateDebutService(),
                panne.getAvisAuNavPdf(),
                panne.getEmailDeclarant(),
                panne.getArchive(),
                panne.getTraitee(),
                panne.getFeu() != null ? panne.getFeu().getId() : null,
                panne.getRegion() != null ? panne.getRegion().getId() : null,
                panne.getProvince() != null ? panne.getProvince().getId() : null,
                panne.getFeu(),
                panne.getRegion(),
                panne.getProvince()
        );
    }

    public static Panne mapToPanne(PanneDto panneDto) {
        Panne newPanne = new Panne();
        newPanne.setId(panneDto.getId());
        newPanne.setNatureDePanne(panneDto.getNatureDePanne());
        newPanne.setDatePanne(panneDto.getDatePanne());
        newPanne.setEtatGeneral("Hors service");
        newPanne.setEtatFonctionnementDeFeuDeSecours(panneDto.getEtatFonctionnementDeFeuDeSecours());
        newPanne.setMotifDePanne(panneDto.getMotifDePanne());
        newPanne.setPlanDAction(panneDto.getPlanDAction());
        newPanne.setDateRemiseEnService(panneDto.getDateRemiseEnService());
        newPanne.setPrevisionDeResolution(panneDto.getPrevisionDeResolution());
        newPanne.setOutOfServiceTime(0.0);
        newPanne.setDateDebutService(panneDto.getDateDebutService());
        newPanne.setAvisAuNavPdf(panneDto.getAvisAuNavPdf());
        newPanne.setEmailDeclarant(panneDto.getEmailDeclarant());
        newPanne.setArchive(0);
        newPanne.setTraitee(0);

        if (panneDto.getIdFeu() != null) {
            Feu feu = new Feu();
            feu.setId(panneDto.getIdFeu());
            newPanne.setFeu(feu);
        }

        if (panneDto.getIdRegion() != null) {
            Region region = new Region();
            region.setId(panneDto.getIdRegion());
            newPanne.setRegion(region);
        }

        if (panneDto.getIdProvince() != null) {
            Province province = new Province();
            province.setId(panneDto.getIdProvince());
            newPanne.setProvince(province);
        }

        return newPanne;
    }

}
