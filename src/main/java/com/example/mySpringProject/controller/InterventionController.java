package com.example.mySpringProject.controller;

import com.example.mySpringProject.model.Intervention;
import com.example.mySpringProject.model.PlanificationIntervention;
import com.example.mySpringProject.service.InterventionService;
import com.example.mySpringProject.service.PlanificationInterventionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api8",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
public class InterventionController {

    private final InterventionService interventionService;
    private  final PlanificationInterventionService planificationInterventionService;

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/addIntervention")
    public ResponseEntity<Intervention> addIntervention(@RequestBody Intervention intervention) {
        Intervention savedIntervention= interventionService.addIntervention(intervention);
        return ResponseEntity.ok(savedIntervention);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/interventions")
    public ResponseEntity<List<Intervention>> getAllInterventions() {
        List<Intervention> interventions = interventionService.getAllInterventions();
        return  ResponseEntity.ok(interventions);
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PutMapping("/updateIntervention/{id}")
    public ResponseEntity<Intervention> updateIntervention(@PathVariable("id") Integer id, @RequestBody Intervention updatedIntervention) {
        Intervention intervention= interventionService.updateIntervention(updatedIntervention,id);
        return ResponseEntity.ok(intervention);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @DeleteMapping("/deleteIntervention/{id}")
    public ResponseEntity<String> deleteIntervention(@PathVariable("id") Integer id) {
        interventionService.deleteIntervention(id);
        return ResponseEntity.ok("Intervention deleted");

    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/addPlanification")
    public ResponseEntity<PlanificationIntervention> addIntervention(@RequestBody PlanificationIntervention intervention) {
        PlanificationIntervention savedIntervention= planificationInterventionService.addPlanification(intervention);
        return ResponseEntity.ok(savedIntervention);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/planifications")
    public ResponseEntity<List<PlanificationIntervention>> getAllPlanifications() {
        List<PlanificationIntervention> interventions = planificationInterventionService.getAllPlanifications();
        return  ResponseEntity.ok(interventions);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @DeleteMapping("/deletePlanification/{id}")
    public ResponseEntity<String> deletePlanification(@PathVariable("id") Integer id) {
        planificationInterventionService.deletePlanification(id);
        return ResponseEntity.ok("Planification deleted");

    }

    }
