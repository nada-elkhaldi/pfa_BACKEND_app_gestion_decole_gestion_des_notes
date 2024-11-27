package com.example.mySpringProject.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity

@Table(name= "provinces")

public class Province {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    private String nomProvince;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idRegion")
    private Region region;

    public Province() {}
    public Province(int id, String nomProvince, Region region) {
        this.id = id;
        this.nomProvince = nomProvince;
        this.region = region;
    }
}
