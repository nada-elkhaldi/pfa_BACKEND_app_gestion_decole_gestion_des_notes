package com.example.mySpringProject.service;

import com.example.mySpringProject.model.Feu;
import com.example.mySpringProject.model.TauDisposability;

import java.util.List;

public interface TauxDisponibiliteService {

    void mettreAJourTauxDisponibilitePourTousLesPhares();

    List<TauDisposability> obtenirTousLesTauxDisponibilite();

    double getTauxDisponibiliteForFeu(Feu feu);
}
