package com.example.mySpringProject.service;


import com.example.mySpringProject.dto.PanneDto;
import com.example.mySpringProject.model.Panne;

import java.util.List;

public interface PanneService {

    PanneDto addPanne(PanneDto panne);
    List<PanneDto> getAllPannes();
    Panne getPanneById(Integer id);
    Panne updatePanne(Integer id, Panne panne);
    void deletePanne(Integer id);
    Panne validatePanne(Integer id);

    void suivrePannes();

    void incrementOutOfServiceTimeForAllPannes();

    double calculerTauxDisponibilite(Integer id);

    Panne updateAvisPath(Integer id, String avisPath);
}
