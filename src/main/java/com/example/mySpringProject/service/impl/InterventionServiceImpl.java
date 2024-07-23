package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.model.Intervention;
import com.example.mySpringProject.model.Panne;
import com.example.mySpringProject.repository.InterventionRepository;
import com.example.mySpringProject.repository.PanneRepository;
import com.example.mySpringProject.service.InterventionService;

import java.util.List;
import java.util.stream.Collectors;

public class InterventionServiceImpl implements InterventionService {

    PanneRepository panneRepository;
    InterventionRepository interventionRepository;

    @Override
    public Intervention addIntervention(Intervention request) {

        Intervention intervention = new Intervention();
        Panne panne = panneRepository.findById(request.getPanne().getId())
                .orElseThrow(() -> new RuntimeException("Panne not found"));

        intervention.setPanne(panne);

        intervention.setIntervenant(request.getIntervenant());
        intervention.setDateIntervention(request.getDateIntervention());
        intervention.setRapportIntervention(request.getRapportIntervention());
        intervention.setCout(request.getCout());
        interventionRepository.save(intervention);
        return intervention;
    }

    @Override
    public List<Intervention> getAllInterventions() {
        List<Intervention> interventions = interventionRepository.findAll();
        return interventions.stream().map((intervention) -> intervention).collect(Collectors.toList());
    }



    @Override
    public Intervention getInterventionById(Integer id) {
        return null;
    }

    @Override
    public Intervention updateIntervention(Intervention intervention) {
        return null;
    }

    @Override
    public void deleteIntervention(Integer id) {

    }
}
