package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.model.Feu;
import com.example.mySpringProject.model.Panne;
import com.example.mySpringProject.model.TauDisposability;
import com.example.mySpringProject.repository.FeuRepository;
import com.example.mySpringProject.repository.PanneRepository;
import com.example.mySpringProject.repository.TauxDisponibiliteRepository;
import com.example.mySpringProject.service.TauxDisponibiliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class TauxDisponibiliteServiceImpl implements TauxDisponibiliteService {


    @Autowired
    private PanneRepository panneRepository;

    @Autowired
    private TauxDisponibiliteRepository tauxDisponibiliteRepository;

    @Autowired
    private FeuRepository feuRepository;


    @Override
    public void mettreAJourTauxDisponibilite(Integer idFeu) {
        System.out.println("Tentative de mise à jour du taux de disponibilité pour le feu avec ID: " + idFeu);

        Feu feu = feuRepository.findById(idFeu)
                .orElseThrow(() -> {
                    System.err.println("Feu avec ID " + idFeu + " non trouvé");
                    return new RuntimeException("Feu not found");
                });

        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().getYear(), 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.now();

        List<Panne> pannes = panneRepository.findByFeu(feu);

        double totalOutOfServiceTime = 0;
        boolean hasPanne = false;

        for (Panne panne : pannes) {
            if ("Hors service".equals(panne.getEtatGeneral())) {
                hasPanne = true;
                totalOutOfServiceTime += panne.getOutOfServiceTime() != null ? panne.getOutOfServiceTime() : 0;

                if (endDate.isBefore(panne.getDatePanne().atStartOfDay())) {
                    endDate = panne.getDatePanne().atStartOfDay();
                }
            }
        }
        double tauxDiso;
        if (hasPanne) {
            Duration serviceDuration = Duration.between(startDate, endDate);
            tauxDiso = ((double) (serviceDuration.toMinutes() - totalOutOfServiceTime) / serviceDuration.toMinutes()) * 100;
        } else {
            tauxDiso = 100.0;
        }

        TauDisposability tauxDisponibilite = tauxDisponibiliteRepository.findByFeu(feu);
        if (tauxDisponibilite == null) {
            tauxDisponibilite = new TauDisposability();
            tauxDisponibilite.setFeu(feu);
        }
        tauxDisponibilite.setTauxCalcule(tauxDiso);
        tauxDisponibilite.setStartDate(startDate.toLocalDate());
        tauxDisponibilite.setEndDate(endDate.toLocalDate());
        tauxDisponibilite.setOutOfServiceTime(totalOutOfServiceTime);

        tauxDisponibiliteRepository.save(tauxDisponibilite);
    }

    @Override
    public double getTauxDisponibiliteForFeu(Feu feu) {
        TauDisposability tauxDisponibilite = tauxDisponibiliteRepository.findByFeu(feu);
        return tauxDisponibilite != null ? tauxDisponibilite.getTauxCalcule() : 100.0;
    }

}
