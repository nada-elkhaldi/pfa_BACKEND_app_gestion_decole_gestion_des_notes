package com.example.mySpringProject.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity

@Table(name= "regions")

public class Region {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    private String nomRegion;

  public Region() {}
    public Region(int id, String nomRegion) {
        this.id = id;
        this.nomRegion = nomRegion;
    }
}
