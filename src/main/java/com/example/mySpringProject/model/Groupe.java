package com.example.mySpringProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "groupes")
public class Groupe {
    @Id
    @GeneratedValue
    private Integer id;
    private String groupName;

    public Groupe(Integer id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public Groupe() {
    }
}