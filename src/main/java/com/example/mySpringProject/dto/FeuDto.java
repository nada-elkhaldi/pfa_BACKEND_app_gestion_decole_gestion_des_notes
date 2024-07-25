package com.example.mySpringProject.dto;

import com.example.mySpringProject.model.Feu;
import com.example.mySpringProject.model.Province;
import com.example.mySpringProject.model.Region;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FeuDto {

    private Integer id;
    private String numero;
    private String nomLocalisation;
    private String position;
    private String caracteristiques;
    private String elevation;
    private String portee;
    private String description;
    private String infos;
    private String etatFonctionnement;
    private String port;
    private String zone;
    private Integer idRegion;
    private Integer idProvince;
    private Region region;
    private Province province;

    public FeuDto(){}

    public FeuDto(Integer id, String numero, String nomLocalisation, String position, String caracteristiques, String elevation, String portee, String description, String infos, String etatFonctionnement, String port, String zone, Integer idRegion, Integer idProvince, Region region, Province province) {
        this.id = id;
        this.numero = numero;
        this.nomLocalisation = nomLocalisation;
        this.position = position;
        this.caracteristiques = caracteristiques;
        this.elevation = elevation;
        this.portee = portee;
        this.description = description;
        this.infos = infos;
        this.etatFonctionnement= etatFonctionnement;
        this.port = port;
        this.zone = zone;
        this.idRegion = idRegion;
        this.idProvince = idProvince;
        this.region = region;
        this.province = province;
    }
}
