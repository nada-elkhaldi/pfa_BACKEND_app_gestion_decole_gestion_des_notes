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

    public Province() {}
    public Province(int id, String nomProvince) {
        this.id = id;
        this.nomProvince = nomProvince;
    }
}
