package com.example.mySpringProject.controller;

import com.example.mySpringProject.dto.MatiereDto;
import com.example.mySpringProject.dto.PlanificationRequest;
import com.example.mySpringProject.model.Classe;
import com.example.mySpringProject.model.Groupe;
import com.example.mySpringProject.model.PlanificationMatiere;
import com.example.mySpringProject.model.Semestre;
import com.example.mySpringProject.service.ClasseService;
import com.example.mySpringProject.service.GroupeService;
import com.example.mySpringProject.service.MatiereService;
import com.example.mySpringProject.service.SemestreService;
import com.example.mySpringProject.service.impl.PlanificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api3",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
public class PlanificationController {
    private final PlanificationService planificationService;
    private final SemestreService semestreService;
    private final ClasseService classeService;
    private final GroupeService groupeService;


    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/addPlanification")
    public ResponseEntity<Void> createPlanifications(@RequestBody List<PlanificationRequest> planificationRequests) {
        planificationService.addPlanifications(planificationRequests);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/planifications")
    public ResponseEntity<List<PlanificationMatiere>> getAllPlanifications() {
        List<PlanificationMatiere> planifications = planificationService.getAllPlanifications();
        return ResponseEntity.ok(planifications);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/planifications/semestre/{semestreId}/classe/{classeId}/groupe/{groupeId}")
    public ResponseEntity<List<PlanificationMatiere>> getPlanificationsBySemestreClasseAndGroupe(
            @PathVariable Integer semestreId,
            @PathVariable Integer classeId,
            @PathVariable Integer groupeId) {


        Semestre semestre = semestreService.getSemestreById(semestreId);
        Classe classe = classeService.getClasseById(classeId);
        Groupe groupe = groupeService.getGroupeById(groupeId);

        List<PlanificationMatiere> planifications = planificationService.getPlanificationsBySemestreClasseAndGroupe(semestre, classe, groupe);
        return ResponseEntity.ok(planifications);
    }


}
