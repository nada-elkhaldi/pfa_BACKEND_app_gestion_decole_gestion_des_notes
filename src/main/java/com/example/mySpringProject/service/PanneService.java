package com.example.mySpringProject.service;


import com.example.mySpringProject.dto.PanneDto;
import com.example.mySpringProject.model.Panne;

import java.time.LocalDate;
import java.util.List;

public interface PanneService {

    PanneDto addPanne(PanneDto panne);
    List<PanneDto> getAllPannes();
    Panne getPanneById(Integer id);
    Panne updatePanne(Integer id, Panne panne);
    Panne annoncerLaReparation(Integer id, Panne panne);
    void deletePanne(Integer id);
    Panne validatePanne(Integer id, String emailDHOC);

    void suivrePannes();

    void incrementOutOfServiceTimeForAllPannes();

    double calculerTauxDisponibilite(Integer id, LocalDate dateDebut);

    //pieces jointes
    Panne updateAvisPath(Integer id, String avisPath);

    List<Panne> getPannesByRegion(Integer regionId);
    List<Panne> findByIds(List<Integer> ids);

    byte[] generatePanneReport(List<Panne> pannes);
}
