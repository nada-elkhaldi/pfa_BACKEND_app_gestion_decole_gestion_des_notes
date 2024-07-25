package com.example.mySpringProject.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter


@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String roleName;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private Set<Privilege> privileges = new HashSet<>();



}

