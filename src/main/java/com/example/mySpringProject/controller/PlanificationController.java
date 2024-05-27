package com.example.mySpringProject.controller;

import com.example.mySpringProject.dto.MatiereDto;
import com.example.mySpringProject.dto.PlanificationRequest;
import com.example.mySpringProject.model.PlanificationMatiere;
import com.example.mySpringProject.service.MatiereService;
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



    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/addPlanification")
    public ResponseEntity<Void> createPlanifications(@RequestBody List<PlanificationRequest> planificationRequests) {
        planificationService.addPlanifications(planificationRequests);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/planifications")
    public ResponseEntity<List<PlanificationMatiere>> getAllPlanifications() {
        List<PlanificationMatiere> planifications = planificationService.getAllPlanifications();
        return ResponseEntity.ok(planifications);
    }


}
