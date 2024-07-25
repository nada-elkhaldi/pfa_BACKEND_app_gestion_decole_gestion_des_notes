package com.example.mySpringProject.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter


@Entity
@Table(name = "privileges")
public class Privilege {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;





}
