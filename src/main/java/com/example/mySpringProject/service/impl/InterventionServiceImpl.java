package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.model.Intervention;
import com.example.mySpringProject.model.Panne;
import com.example.mySpringProject.repository.InterventionRepository;
import com.example.mySpringProject.repository.PanneRepository;
import com.example.mySpringProject.service.InterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterventionServiceImpl implements InterventionService {

    private final PanneRepository panneRepository;
    private final InterventionRepository interventionRepository;

    @Autowired
    public InterventionServiceImpl(PanneRepository panneRepository, InterventionRepository interventionRepository) {
        this.panneRepository = panneRepository;
        this.interventionRepository = interventionRepository;
    }

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
    public Intervention updateIntervention(Intervention request, Integer id) {
        Intervention intervention1 = interventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention with id " + id + " not found"));

        intervention1.setDateIntervention(request.getDateIntervention());
        intervention1.setRapportIntervention(request.getRapportIntervention());
        intervention1.setCout(request.getCout());


        Intervention savedIntervention = interventionRepository.save(intervention1);
        return savedIntervention;
    }

    @Override
    public void deleteIntervention(Integer id) {

        Intervention intervention= interventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention with id " + id + " not found"));

        interventionRepository.deleteById(id);
    }
}
