package com.example.mySpringProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity

@Table(name= "feux")
public class Feu {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;
    @Column(name="numero")
    private String numero;
    @Column(name="nomLocalisation")
    private String nomLocalisation;
    @Column(name="position")
    private String position;
    @Column(name="caracteristiques")
    private String caracteristiques;
    @Column(name="elevation")
    private String elevation;
    @Column(name="portee")
    private String portee;

    @Column(name="description")
    private String description;

    @Column(name="infos")
    private String infos;

    public Feu(){}
    public Feu(Integer id, String numero, String nomLocalisation, String position, String caracteristiques, String elevation, String portee, String description, String infos) {
        this.id = id;
        this.numero = numero;
        this.nomLocalisation = nomLocalisation;
        this.position = position;
        this.caracteristiques = caracteristiques;
        this.elevation = elevation;
        this.portee = portee;
        this.description = description;
        this.infos = infos;
    }
}
