package com.example.mySpringProject.service;

import com.example.mySpringProject.model.Feu;
import com.example.mySpringProject.model.TauDisposability;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TauxDisponibiliteService {

    void mettreAJourTauxDisponibilitePourTousLesPhares(LocalDate startDate, LocalDate endDate);

    List<TauDisposability> obtenirTousLesTauxDisponibilite();

    double getTauxDisponibiliteForFeu(Feu feu);

    List<TauDisposability> getTauxDisponibiliteByRegionId(Integer regionId);
}
