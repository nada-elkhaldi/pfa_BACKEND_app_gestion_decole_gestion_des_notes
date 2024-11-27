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
    private String avisAuNavPdf;
    private String emailDeclarant;

    private Integer archive;
    private Integer traitee;

    private Integer idFeu;

    private Integer idProvince;

    private Province province;
    private Feu feu;



    // Assurez-vous que ce constructeur existe
    public PanneDto(Integer id, String natureDePanne, LocalDate datePanne, String etatGeneral, String etatFonctionnementDeFeuDeSecours, String motifDePanne, String planDAction, LocalDate dateRemiseEnService, LocalDate previsionDeResolution, Double outOfServiceTime, LocalDate dateDebutService, String avisAuNavPdf, String emailDeclarant,Integer archive, Integer traitee, Integer idFeu, Integer idProvince, Feu feu, Province province) {
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
        this.avisAuNavPdf = avisAuNavPdf;
        this.emailDeclarant = emailDeclarant;
        this.archive=archive;
        this.traitee=traitee;
        this.idFeu = idFeu;

        this.idProvince = idProvince;
        this.feu = feu;

        this.province = province;
    }


}
