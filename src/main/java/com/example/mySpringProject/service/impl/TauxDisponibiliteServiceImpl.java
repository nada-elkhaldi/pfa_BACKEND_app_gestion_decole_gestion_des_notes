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



    private final PanneRepository panneRepository;

    private final TauxDisponibiliteRepository tauxDisponibiliteRepository;

    private final FeuRepository feuRepository;

    @Autowired
    public TauxDisponibiliteServiceImpl(PanneRepository panneRepository, TauxDisponibiliteRepository tauxDisponibiliteRepository, FeuRepository feuRepository) {
        this.panneRepository = panneRepository;
        this.tauxDisponibiliteRepository = tauxDisponibiliteRepository;
        this.feuRepository = feuRepository;
    }

    // Le meme traitement de calcul de taux de dispo (avec les minutes) pour le test
//    @Override
//    public void mettreAJourTauxDisponibilitePourTousLesPhares(LocalDate startDate, LocalDate endDate) {
//        System.out.println("Tentative de mise à jour du taux de disponibilité pour tous les phares");
//
//        List<Feu> tousLesPhares = feuRepository.findAll();
//
//        for (Feu feu : tousLesPhares) {
//            System.out.println("Traitement du feu avec ID: " + feu.getId());
//
//            // Conversion LocalDate en LocalDateTime avec heure de début de journée
//            LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : LocalDate.now().withDayOfYear(1).atStartOfDay();
//            LocalDateTime endDateTime = (endDate != null) ? endDate.atStartOfDay() : LocalDateTime.now();
//
//            List<Panne> pannes = panneRepository.findByFeu(feu);
//
//            double totalOutOfServiceTime = 0;
//            boolean hasPanne = false;
//
//            for (Panne panne : pannes) {
//                //if ("Hors service".equals(panne.getEtatGeneral())) {
//                    hasPanne = true;
//                    totalOutOfServiceTime += panne.getOutOfServiceTime() != null ? panne.getOutOfServiceTime() : 0;
//
//                    LocalDateTime panneDateTime = panne.getDatePanne().atStartOfDay();
//                    if (endDateTime.isBefore(panneDateTime)) {
//                        endDateTime = panneDateTime;
//                   // }
//                }
//            }
//
//            double tauxDiso;
//            if (hasPanne) {
//                Duration serviceDuration = Duration.between(startDateTime, endDateTime);
//                long totalMinutes = serviceDuration.toMinutes();
//                tauxDiso = ((double) (totalMinutes - totalOutOfServiceTime) / totalMinutes) * 100;
//            } else {
//                tauxDiso = 100.0;
//            }
//
//            // Trouver ou créer le taux de disponibilité pour le phare
//            TauDisposability tauxDisponibilite = tauxDisponibiliteRepository.findByFeu(feu);
//            if (tauxDisponibilite == null) {
//                tauxDisponibilite = new TauDisposability();
//                tauxDisponibilite.setFeu(feu);
//            }
//            tauxDisponibilite.setTauxCalcule(tauxDiso);
//            tauxDisponibilite.setStartDate(startDateTime.toLocalDate());
//            tauxDisponibilite.setEndDate(endDateTime.toLocalDate());
//            tauxDisponibilite.setOutOfServiceTime(totalOutOfServiceTime);
//
//            // Sauvegarder ou mettre à jour le taux de disponibilité
//            tauxDisponibiliteRepository.save(tauxDisponibilite);
//
//            System.out.println("Taux de disponibilité mis à jour pour le feu avec ID: " + feu.getId());
//        }
//
//        System.out.println("Mise à jour du taux de disponibilité terminée pour tous les phares");
//    }

    @Override
    public void mettreAJourTauxDisponibilitePourTousLesPhares(LocalDate startDate, LocalDate endDate) {
        System.out.println("Tentative de mise à jour du taux de disponibilité pour tous les phares");

        List<Feu> tousLesPhares = feuRepository.findAll();

        for (Feu feu : tousLesPhares) {
            System.out.println("Traitement du feu avec ID: " + feu.getId());

            // Conversion LocalDate en LocalDateTime avec heure de début de journée
            LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : LocalDate.now().withDayOfYear(1).atStartOfDay();
            LocalDateTime endDateTime = (endDate != null) ? endDate.atStartOfDay() : LocalDateTime.now();

            List<Panne> pannes = panneRepository.findByFeu(feu);

            double totalOutOfServiceTimeInHours = 0; // Le temps d'indisponibilité est en heures
            boolean hasPanne = false;

            for (Panne panne : pannes) {
                hasPanne = true;
                totalOutOfServiceTimeInHours += panne.getOutOfServiceTime() != null ? panne.getOutOfServiceTime() : 0;

                LocalDateTime panneDateTime = panne.getDatePanne().atStartOfDay();
                if (endDateTime.isBefore(panneDateTime)) {
                    endDateTime = panneDateTime;
                }
            }

            double tauxDiso;
            if (hasPanne) {
                Duration serviceDuration = Duration.between(startDateTime, endDateTime);
                double totalServiceTimeInDays = serviceDuration.toDays();
                double totalOutOfServiceTimeInDays = totalOutOfServiceTimeInHours / 24.0; // Convertir le temps d'indisponibilité en jours
                tauxDiso = ((totalServiceTimeInDays - totalOutOfServiceTimeInDays) / totalServiceTimeInDays) * 100;
            } else {
                tauxDiso = 100.0;
            }

            // Trouver ou créer le taux de disponibilité pour le phare
            TauDisposability tauxDisponibilite = tauxDisponibiliteRepository.findByFeu(feu);
            if (tauxDisponibilite == null) {
                tauxDisponibilite = new TauDisposability();
                tauxDisponibilite.setFeu(feu);
            }
            tauxDisponibilite.setTauxCalcule(tauxDiso);
            tauxDisponibilite.setStartDate(startDateTime.toLocalDate());
            tauxDisponibilite.setEndDate(endDateTime.toLocalDate());
            tauxDisponibilite.setOutOfServiceTime(totalOutOfServiceTimeInHours); // Conserver en heures pour la sauvegarde

            // Sauvegarder ou mettre à jour le taux de disponibilité
            tauxDisponibiliteRepository.save(tauxDisponibilite);

            System.out.println("Taux de disponibilité mis à jour pour le feu avec ID: " + feu.getId());
        }

        System.out.println("Mise à jour du taux de disponibilité terminée pour tous les phares");
    }



    @Override
    public List<TauDisposability> obtenirTousLesTauxDisponibilite() {
        return tauxDisponibiliteRepository.findAll();
    }

    @Override
    public double getTauxDisponibiliteForFeu(Feu feu) {
        TauDisposability tauxDisponibilite = tauxDisponibiliteRepository.findByFeu(feu);
        return tauxDisponibilite != null ? tauxDisponibilite.getTauxCalcule() : 100.0;
    }

    @Override
    public List<TauDisposability> getTauxDisponibiliteByRegionId(Integer regionId) {
        return tauxDisponibiliteRepository.findByFeuRegionId(regionId);
    }

}
