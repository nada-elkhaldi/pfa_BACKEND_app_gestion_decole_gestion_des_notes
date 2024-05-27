package com.example.mySpringProject.dto;


import com.example.mySpringProject.model.Classe;
import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.model.User;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class GroupeDto {

    private Integer id;
    private String groupName;
    private Matiere matiere;
    private List<User> etudiants= new ArrayList<>();

    public GroupeDto(Integer id, String groupName, Matiere matiere) {
        this.id = id;
        this.groupName = groupName;

        this.matiere=matiere;
    }


}
