package com.example.mySpringProject.service;

import com.example.mySpringProject.dto.SemestreDto;

import java.util.List;

public interface SemestreService {

    SemestreDto createSemestre(SemestreDto semestreDto);
    List<SemestreDto> getAllSemestre();
}
