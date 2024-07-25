package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.model.Intervention;
import com.example.mySpringProject.model.Panne;
import com.example.mySpringProject.model.PlanificationIntervention;
import com.example.mySpringProject.repository.InterventionRepository;
import com.example.mySpringProject.repository.PanneRepository;
import com.example.mySpringProject.repository.PlanificationInterventionRepository;
import com.example.mySpringProject.service.PlanificationInterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PlanificationInterServiceImpl implements PlanificationInterventionService {

    private final PanneRepository panneRepository;
    private final PlanificationInterventionRepository planificationInterventionRepository;

    @Autowired
    public PlanificationInterServiceImpl(PanneRepository panneRepository, PlanificationInterventionRepository interventionRepository) {
        this.panneRepository = panneRepository;
        this.planificationInterventionRepository = interventionRepository;
    }

    @Override
    public PlanificationIntervention addPlanification(PlanificationIntervention request) {
        PlanificationIntervention intervention = new PlanificationIntervention();
        Panne panne = panneRepository.findById(request.getPanne().getId())
                .orElseThrow(() -> new RuntimeException("Panne not found"));

        intervention.setPanne(panne);
        intervention.setDescriptif(request.getDescriptif());
        intervention.setEchancier(request.getEchancier());
        planificationInterventionRepository.save(intervention);
        return intervention;
    }

    @Override
    public List<PlanificationIntervention> getAllPlanifications() {
        List<PlanificationIntervention> interventions = planificationInterventionRepository.findAll();
        return interventions.stream().map((intervention) -> intervention).collect(Collectors.toList());
    }

    @Override
    public PlanificationIntervention getInterventionById(Integer id) {
        return null;
    }

    @Override
    public PlanificationIntervention updateIntervention(PlanificationIntervention intervention, Integer id) {
        PlanificationIntervention intervention1 = planificationInterventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention with id " + id + " not found"));

        intervention1.setEchancier(intervention.getEchancier());
        intervention1.setDescriptif(intervention.getDescriptif());

        PlanificationIntervention savedIntervention = planificationInterventionRepository.save(intervention1);
        return savedIntervention;
    }

    @Override
    public void deletePlanification(Integer id) {

        PlanificationIntervention intervention= planificationInterventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention with id " + id + " not found"));

        planificationInterventionRepository.deleteById(id);
    }
}
