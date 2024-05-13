package com.example.mySpringProject.service;


import com.example.mySpringProject.dto.MatiereDto;

import java.util.List;

public interface MatiereService {

    List<MatiereDto> createMatiere(List<MatiereDto> matiereDto);
    List<MatiereDto> getAllMatieres();
}
