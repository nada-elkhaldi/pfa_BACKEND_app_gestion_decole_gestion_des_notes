package com.example.mySpringProject.dto;


import com.example.mySpringProject.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

public class MatiereDto {
    private Integer id;
    private String code;
    private String intitule;
   //private List<Note> notes;

    public MatiereDto(Integer id, String code, String intitule) {
        this.id = id;
        this.code = code;
        this.intitule = intitule;

    }
}
