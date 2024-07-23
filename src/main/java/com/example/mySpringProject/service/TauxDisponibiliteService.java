package com.example.mySpringProject.service;

import com.example.mySpringProject.model.Feu;

public interface TauxDisponibiliteService {

    void mettreAJourTauxDisponibilite(Integer idFeu);

    double getTauxDisponibiliteForFeu(Feu feu);
}
