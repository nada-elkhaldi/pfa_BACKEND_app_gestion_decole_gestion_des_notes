package com.example.mySpringProject.model;




import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity

@Table(name= "budgets")
public class Budget {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;
    private String annee;
    private Double montantTotal;
    private Double montantUtilise;
    private Double montantDisponible;

    @Enumerated(EnumType.STRING)
    private Categorie categorie;


    @ManyToOne(optional = true)
    @JoinColumn(name = "idRegion")
    private Region region;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idProvince")
    private Province province;
}
