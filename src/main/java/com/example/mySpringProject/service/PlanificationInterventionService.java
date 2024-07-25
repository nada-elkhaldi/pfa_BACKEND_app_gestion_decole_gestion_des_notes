package com.example.mySpringProject.service;


import com.example.mySpringProject.model.PlanificationIntervention;

import java.util.List;

public interface PlanificationInterventionService {

    PlanificationIntervention addPlanification(PlanificationIntervention intervention);
    List<PlanificationIntervention> getAllPlanifications();
    PlanificationIntervention getInterventionById(Integer id);
    PlanificationIntervention updateIntervention(PlanificationIntervention intervention, Integer id);
    void deletePlanification(Integer id);
}
