package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.dto.PlanificationRequest;
import com.example.mySpringProject.model.*;
import com.example.mySpringProject.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanificationService {
    private final PlanificationRepository planificationRepository;
    private final MatiereRepository matiereRepository;
    private final UserRepository enseignantRepository;
    private final GroupeRepository groupeRepository;
    private final SemestreRepository semestreRepository;
    private final ClasseRepository classeRepository;

    public PlanificationService(PlanificationRepository planificationRepository, MatiereRepository matiereRepository, UserRepository enseignantRepository, GroupeRepository groupeRepository, SemestreRepository semestreRepository, ClasseRepository classeRepository) {
        this.planificationRepository = planificationRepository;
        this.matiereRepository = matiereRepository;
        this.enseignantRepository = enseignantRepository;
        this.groupeRepository = groupeRepository;
        this.semestreRepository = semestreRepository;
        this.classeRepository = classeRepository;
    }


    public void addPlanifications(List<PlanificationRequest> planificationRequests) {
        for (PlanificationRequest request : planificationRequests) {

            Classe classe = classeRepository.findById(request.getClasseId())
                    .orElseThrow(() -> new RuntimeException("Class not found"));

            Semestre semester = semestreRepository.findById(request.getSemesterId())
                    .orElseThrow(() -> new RuntimeException("Semester not found"));

            Groupe groupe = groupeRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found"));

            Matiere matiere = matiereRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));

            User enseignant = enseignantRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            PlanificationMatiere planification = new PlanificationMatiere();
            planification.setSemestre(semester);
            planification.setGroupe(groupe);
            planification.setMatiere(matiere);
            planification.setEnseignant(enseignant);
            planification.setClasse(classe);
            planificationRepository.save(planification);
        }
    }
    public List<PlanificationMatiere> getAllPlanifications() {
        return planificationRepository.findAll();
    }

    public List<PlanificationMatiere> getPlanificationsBySemestreClasseAndGroupe(Semestre semestre, Classe classe, Groupe groupe) {
        return planificationRepository.findBySemestreAndClasseAndGroupe(semestre, classe, groupe);
    }
}
