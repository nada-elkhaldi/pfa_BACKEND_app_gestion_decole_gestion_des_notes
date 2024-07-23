package com.example.mySpringProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@Entity
@Table(name = "declarations")
public class Declaration {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    private LocalDate dateDeclaration;
    private String tyeDeclaration;
    @ManyToOne
    @JoinColumn(name = "id_panne")
    private Panne panne;

    @ManyToOne
    @JoinColumn(name = "id_declarant")
    private User declarant;

}
