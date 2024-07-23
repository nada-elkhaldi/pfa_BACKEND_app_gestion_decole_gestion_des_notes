package com.example.mySpringProject.service;

import com.example.mySpringProject.model.Intervention;

import java.util.List;

public interface InterventionService {
    Intervention addIntervention(Intervention intervention);
    List<Intervention> getAllInterventions();
    Intervention getInterventionById(Integer id);
    Intervention updateIntervention(Intervention intervention);
    void deleteIntervention(Integer id);
}
