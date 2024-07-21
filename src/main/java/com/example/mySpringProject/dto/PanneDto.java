package com.example.mySpringProject.dto;


import com.example.mySpringProject.model.Feu;
import com.example.mySpringProject.model.Province;
import com.example.mySpringProject.model.Region;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class PanneDto {
    private Integer id;

    private String natureDePanne;
    private LocalDate datePanne;
    private String etatGeneral;
    private String etatFonctionnementDeFeuDeSecours;
    private String motifDePanne;
    private String planDAction;
    private LocalDate dateRemiseEnService;
    private LocalDate previsionDeResolution;
    private Double outOfServiceTime;
    private LocalDate dateDebutService;
    private Double tauxDeDisponibilite;
    private String avisAuNavPdf;
    private String emailDHOC;
    private String emailDPDPM;
    private String emailDeclarant;

    private Integer idFeu;
    private Integer idRegion;
    private Integer idProvince;
    private Feu feu;
    private Region region;
    private Province province;

    public PanneDto(Integer id, String natureDePanne, LocalDate datePanne, String etatGeneral, String etatFonctionnementDeFeuDeSecours, String motifDePanne, String planDAction, LocalDate dateRemiseEnService, LocalDate previsionDeResolution, Double outOfServiceTime, LocalDate dateDebutService, Double tauxDeDisponibilite, String avisAuNavPdf, String emailDHOC, String emailDPDPM, String emailDeclarant, Integer idFeu, Integer idRegion, Integer idProvince, Feu feu, Region region, Province province) {
        this.id = id;
        this.natureDePanne = natureDePanne;
        this.datePanne = datePanne;
        this.etatGeneral = etatGeneral;
        this.etatFonctionnementDeFeuDeSecours = etatFonctionnementDeFeuDeSecours;
        this.motifDePanne = motifDePanne;
        this.planDAction = planDAction;
        this.dateRemiseEnService = dateRemiseEnService;
        this.previsionDeResolution = previsionDeResolution;
        this.outOfServiceTime = outOfServiceTime;
        this.dateDebutService = dateDebutService;
        this.tauxDeDisponibilite = tauxDeDisponibilite;
        this.avisAuNavPdf = avisAuNavPdf;
        this.emailDHOC = emailDHOC;
        this.emailDPDPM = emailDPDPM;
        this.emailDeclarant = emailDeclarant;
        this.idFeu = idFeu;
        this.idRegion = idRegion;
        this.idProvince = idProvince;
        this.feu = feu;
        this.region = region;
        this.province = province;
    }
}
