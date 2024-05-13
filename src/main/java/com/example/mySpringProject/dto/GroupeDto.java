package com.example.mySpringProject.dto;


import com.example.mySpringProject.model.Classe;
import com.example.mySpringProject.model.Matiere;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class GroupeDto {

    private Integer id;
    private String groupName;
    private Classe classe;
    private Matiere matiere;

    public GroupeDto(Integer id, String groupName, Classe classe, Matiere matiere) {
        this.id = id;
        this.groupName = groupName;
        this.classe=classe;
        this.matiere=matiere;
    }
}
