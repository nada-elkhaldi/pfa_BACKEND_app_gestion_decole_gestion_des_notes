package com.example.mySpringProject.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity

@Table(name= "pannes")
public class Panne {
    @Id
    @GeneratedValue
    @Column(name="id")
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

    @ManyToOne(optional = true)
    @JoinColumn(name = "idFeu")
    private Feu feu;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idRegion")
    private Region region;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idProvince")
    private Province province;

    // Champs pour suivre les périodes hors service
    @Column(name = "out_of_service_start_time")
    private LocalDateTime outOfServiceStartTime;

    @Column(name = "out_of_service_end_time")
    private LocalDateTime outOfServiceEndTime;


    public Panne() {}
    public Panne(Integer id, String natureDePanne, LocalDate datePanne, String typeDeclaration, String etatGeneral, String etatFonctionnementDeFeuDeSecours, String motifDePanne, String planDAction, LocalDate dateRemiseEnService, LocalDate previsionDeResolution, Double outOfServiceTime, LocalDate dateDebutService, LocalDate dateFinService, Double tauxDeDisponibilite, String rapportPdf, String avisAuNavPdf, String piecesJointes, String emailDHOC, String emailDPDPM, String emailDeclarant, Feu feu, Region region, Province province    ) {
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
        this.feu = feu;
        this.region = region;
        this.province = province;

    }



    public void incrementOutOfServiceTime() {
        if (this.etatGeneral.equals("hors service")) {
            LocalDateTime now = LocalDateTime.now();
            if (now.getHour() >= 18 || now.getHour() < 6) {
                this.outOfServiceTime++;
            }
        }
    }

    // Method to stop counting out of service time
    public void stopOutOfServiceTime() {
        if ("remise en service".equals(this.etatGeneral) && this.outOfServiceStartTime != null) {
            Duration duration = Duration.between(this.outOfServiceStartTime, LocalDateTime.now());
            this.outOfServiceTime += duration.toHours();
            this.outOfServiceStartTime = null;
        }

    }
}
