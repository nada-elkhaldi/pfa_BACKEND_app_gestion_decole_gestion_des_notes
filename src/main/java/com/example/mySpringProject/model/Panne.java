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
@Table(name = "pannes")
public class Panne {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    private String natureDePanne;
    private LocalDate datePanne;
    private String etatGeneral;
    private String etatFonctionnementDeFeuDeSecours;
    private String motifDePanne;
    private String planDAction;
    private LocalDate dateRemiseEnService;
    private LocalDate previsionDeResolution;
    private Double outOfServiceTime = 0.0;
    private LocalDate dateDebutService;
    private String avisAuNavPdf;
    private String emailDeclarant;

    private Integer archive;
    private Integer traitee;
    @ManyToOne(optional = true)
    @JoinColumn(name = "idFeu")
    private Feu feu;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idRegion")
    private Region region;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idProvince")
    private Province province;

    @Column(name = "out_of_service_start_time")
    private LocalDateTime outOfServiceStartTime;

    @Column(name = "out_of_service_end_time")
    private LocalDateTime outOfServiceEndTime;

    public Panne() {

        this.outOfServiceTime = 0.0;
    }

    public Panne(Integer id, String natureDePanne, LocalDate datePanne, String etatGeneral, String etatFonctionnementDeFeuDeSecours, String motifDePanne, String planDAction, LocalDate dateRemiseEnService, LocalDate previsionDeResolution, Double outOfServiceTime, LocalDate dateDebutService, String avisAuNavPdf, String emailDeclarant, Feu feu, Region region, Province province) {
        this.id = id;
        this.natureDePanne = natureDePanne;
        this.datePanne = datePanne;
        this.etatGeneral =  etatGeneral ;
        this.etatFonctionnementDeFeuDeSecours = etatFonctionnementDeFeuDeSecours;
        this.motifDePanne = motifDePanne;
        this.planDAction = planDAction;
        this.dateRemiseEnService = dateRemiseEnService;
        this.previsionDeResolution = previsionDeResolution;
        this.outOfServiceTime = (outOfServiceTime != null) ? outOfServiceTime : 0.0;
        this.dateDebutService = dateDebutService;
        this.avisAuNavPdf = avisAuNavPdf;
        this.emailDeclarant = emailDeclarant;
        this.feu = feu;
        this.region = region;
        this.province = province;
    }

//    public void incrementOutOfServiceTime() {
//        if ("Hors service".equals(this.etatGeneral)) {
//            if (this.outOfServiceTime == null) {
//                this.outOfServiceTime = 0.0;
//            }
//            this.outOfServiceTime++;
//        }
//    }

    public void incrementOutOfServiceTime() {
        if (this.outOfServiceTime == null) {
            this.outOfServiceTime = 0.0;
        }
        if (this.etatGeneral.equals("Hors service")) {
            LocalDateTime now = LocalDateTime.now();
           if (now.getHour() >= 18 || now.getHour() < 6) {
                this.outOfServiceTime++;
          }
        }
    }

    public void stopOutOfServiceTime() {
        if ("Remise en service".equals(this.etatGeneral) && this.outOfServiceStartTime != null) {
            Duration duration = Duration.between(this.outOfServiceStartTime, LocalDateTime.now());
            if (this.outOfServiceTime == null) {
                this.outOfServiceTime = 0.0;
            }
            this.outOfServiceTime += duration.toHours();
            this.outOfServiceStartTime = null;
        }
    }
}
