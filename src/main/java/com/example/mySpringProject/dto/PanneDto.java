package com.example.mySpringProject.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class PanneDto {
    private Integer id;
    private String natureDePanne;
    private LocalDate datePanne;
    private String typeDeclaration;
    private String etatGeneral;
    private String etatFonctionnementDeFeuDeSecours;
    private String motifDePanne;
    private String planDAction;
    private LocalDate dateRemiseEnService;
    private LocalDate previsionDeResolution;
    private Double outOfServiceTime;
    private LocalDate dateDebutService;
    private LocalDate dateFinService;
    private Double tauxDeDisponibilite;
    private String rapportPdf;
    private String avisAuNavPdf;
    private String piecesJointes;
    private String emailDHOC;
    private String emailDPDPM;
    private String emailDeclarant;

    private Integer idFeu;
    private Integer idRegion;
    private Integer idProvince;

    public PanneDto(Integer id, String natureDePanne, LocalDate datePanne, String typeDeclaration, String etatGeneral, String etatFonctionnementDeFeuDeSecours, String motifDePanne, String planDAction, LocalDate dateRemiseEnService, LocalDate previsionDeResolution, Double outOfServiceTime, LocalDate dateDebutService, LocalDate dateFinService, Double tauxDeDisponibilite, String rapportPdf, String avisAuNavPdf, String piecesJointes, String emailDHOC, String emailDPDPM, String emailDeclarant, Integer idFeu, Integer idRegion, Integer idProvince) {
        this.id = id;
        this.natureDePanne = natureDePanne;
        this.datePanne = datePanne;
        this.typeDeclaration = typeDeclaration;
        this.etatGeneral = etatGeneral;
        this.etatFonctionnementDeFeuDeSecours = etatFonctionnementDeFeuDeSecours;
        this.motifDePanne = motifDePanne;
        this.planDAction = planDAction;
        this.dateRemiseEnService = dateRemiseEnService;
        this.previsionDeResolution = previsionDeResolution;
        this.outOfServiceTime = outOfServiceTime;
        this.dateDebutService = dateDebutService;
        this.dateFinService = dateFinService;
        this.tauxDeDisponibilite = tauxDeDisponibilite;
        this.rapportPdf = rapportPdf;
        this.avisAuNavPdf = avisAuNavPdf;
        this.piecesJointes = piecesJointes;
        this.emailDHOC = emailDHOC;
        this.emailDPDPM = emailDPDPM;
        this.emailDeclarant = emailDeclarant;
        this.idFeu = idFeu;
        this.idRegion = idRegion;
        this.idProvince = idProvince;
    }
}
