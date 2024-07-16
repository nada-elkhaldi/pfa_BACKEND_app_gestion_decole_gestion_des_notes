package com.example.mySpringProject.service.impl;


import com.example.mySpringProject.dto.PanneDto;
import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.mapper.PanneMapper;
import com.example.mySpringProject.model.*;
import com.example.mySpringProject.repository.FeuRepository;
import com.example.mySpringProject.repository.PanneRepository;
import com.example.mySpringProject.repository.ProvinceRepository;
import com.example.mySpringProject.repository.RegionRepository;
import com.example.mySpringProject.service.PanneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PanneServiceImpl implements PanneService {

    private final EmailService emailService;
    private final PanneRepository panneRepository;
    private final FeuRepository feuRepository;
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final PanneMapper panneMapper;

    @Autowired
    public PanneServiceImpl(PanneRepository panneRepository, EmailService emailService, FeuRepository feuRepository, ProvinceRepository provinceRepository, RegionRepository regionRepository, PanneMapper panneMapper) {

        this.emailService = emailService;
        this.panneRepository = panneRepository;
        this.feuRepository = feuRepository;
        this.provinceRepository = provinceRepository;
        this.regionRepository = regionRepository;
        this.panneMapper = panneMapper;
    }


    @Override
    public PanneDto addPanne(PanneDto panneDto) {
        Panne newPanne = PanneMapper.mapToPanne(panneDto);

        Feu feu = feuRepository.findById(panneDto.getIdFeu())
                .orElseThrow(() -> new RuntimeException("Feu not found"));
        Region region = regionRepository.findById(panneDto.getIdRegion())
                .orElseThrow(() -> new RuntimeException("Region not found"));
        Province province = provinceRepository.findById(panneDto.getIdProvince())
                .orElseThrow(() -> new RuntimeException("Province not found"));

        newPanne.setFeu(feu);
        newPanne.setRegion(region);
        newPanne.setProvince(province);
        Panne savedPanne = panneRepository.save(newPanne);

        Email email = new Email();
        email.setRecipient(savedPanne.getEmailDPDPM());
        email.setSubject("Notification de panne de phare");

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Bonjour DPDPM,\n\n")
                .append("Nous vous informons qu'une panne a été déclarée pour le phare suivant :\n\n")
                .append("   - Type de déclaration : ").append(savedPanne.getTypeDeclaration()).append("\n")
                .append("   - Nom du phare : ")
                .append(savedPanne.getFeu().getNomLocalisation()).append(savedPanne.getFeu().getPosition()).append("\n")
                .append("   - Région : ").append(savedPanne.getRegion().getNomRegion()).append("\n")
                .append("   - Province : ").append(savedPanne.getProvince().getNomProvince()).append("\n")
                .append("   - Date de la panne : ").append(savedPanne.getDatePanne()).append("\n")
                .append("   - État de fonctionnement des feux de secours : ").append(savedPanne.getEtatFonctionnementDeFeuDeSecours()).append("\n\n")
                .append("Veuillez prendre les mesures nécessaires pour résoudre ce problème.\n\n")
                .append("Cordialement,\n")
                .append("L'équipe de gestion des phares");

        email.setBody(emailBody.toString());
        String result = emailService.sendMail(email);
        if (!result.equals("Email sent")) {
            throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
        }
        return PanneMapper.mapToPanDto(savedPanne);
    }


    @Override
    public List<PanneDto> getAllPannes() {
        List<Panne> pannes = panneRepository.findAll();
        return pannes.stream().map((panne)-> panneMapper.mapToPanDto(panne))
                .collect(Collectors.toList());
    }

    @Override
    public Panne getPanneById(Integer id) {
        return panneRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Panne with id " + id + " not found"));

    }

    @Override
    public Panne updatePanne(Integer id, Panne request) {
        Panne panne = panneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panne with id " + id + " not found"));


        if (!panne.getEtatGeneral().equals(request.getEtatGeneral())) {
            if (request.getEtatGeneral().equals("remise en service")) {
                panne.stopOutOfServiceTime();
            }
        }
        panne.setNatureDePanne(request.getNatureDePanne());
        panne.setDatePanne(request.getDatePanne());
        panne.setMotifDePanne(request.getMotifDePanne());
        panne.setEmailDPDPM(request.getEmailDPDPM());
        panne.setPlanDAction(request.getPlanDAction());
        panne.setPrevisionDeResolution(request.getPrevisionDeResolution());
        panne.setTauxDeDisponibilite(request.getTauxDeDisponibilite());
        panne.setDateRemiseEnService(request.getDateRemiseEnService());
        panne.setEtatFonctionnementDeFeuDeSecours(request.getEtatFonctionnementDeFeuDeSecours());
        panne.setEtatGeneral(request.getEtatGeneral());
        panne.setTypeDeclaration(request.getTypeDeclaration());
        panne.setOutOfServiceTime(request.getOutOfServiceTime());
        panne.setDateDebutService(request.getDateDebutService());
        panne.setDateFinService(request.getDateFinService());
        panne.setRapportPdf(request.getRapportPdf());
        panne.setAvisAuNavPdf(request.getAvisAuNavPdf());
        panne.setEmailDHOC(request.getEmailDHOC());
        panne.setEmailDeclarant(request.getEmailDeclarant());


        Panne savedPanne = panneRepository.save(panne);
        return savedPanne;
    }


    @Override
    public void deletePanne(Integer id) {

           Panne panne = panneRepository.findById(id).orElseThrow(
                    ()-> new ResourceNotFoundException("Panne with id " + id + " not found")
            );
            panneRepository.deleteById(id);

    }

    @Override
    public Panne validatePanne(Integer id) {
        Panne panne = panneRepository.findById(id).orElseThrow(() -> new RuntimeException("Panne not found"));
        panne.setEtatGeneral("hors service");
        panne.incrementOutOfServiceTime();
        panneRepository.save(panne);

        Email email = new Email();
        email.setRecipient(panne.getEmailDHOC());
        email.setSubject("Notification de panne de phare");

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Bonjour DHOC,\n\n")
                .append("Nous vous informons qu'une panne a été déclarée et validée pour le phare suivant :\n\n")
                .append("   - Type de déclaration : ").append(panne.getTypeDeclaration()).append("\n")
                .append("   - Nom du phare : ")
                .append(panne.getFeu().getNomLocalisation()).append(panne.getFeu().getPosition()).append("\n")
                .append("   - Région : ").append(panne.getRegion().getNomRegion()).append("\n")
                .append("   - Province : ").append(panne.getProvince().getNomProvince()).append("\n")
                .append("   - Date de la panne : ").append(panne.getDatePanne()).append("\n")
                .append("   - État de fonctionnement des feux de secours : ").append(panne.getEtatFonctionnementDeFeuDeSecours()).append("\n\n")
                .append("Veuillez prendre les mesures nécessaires pour résoudre ce problème.\n\n")
                .append("Cordialement,\n")
                .append("L'équipe de gestion des phares");

        email.setBody(emailBody.toString());
        String result = emailService.sendMail(email);
        if (!result.equals("Email sent")) {
            throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
        }

        return panne;
    }
//    // Méthode pour suivre automatiquement les pannes et envoyer des alertes si nécessaire
//    @Scheduled(fixedRate = 60000) // Exécuter toutes les 60 secondes (ajustez selon vos besoins)
//    public void suivrePannes() {
//        List<Panne> pannes = panneRepository.findByEtatGeneral("hors service");
//
//        for (Panne panne : pannes) {
//            LocalDate now = LocalDate.now();
//            Period period = Period.between(panne.getDatePanne(), now);
//            long daysSincePanne = period.getDays();
//
//            if (daysSincePanne > 15) {
//
//                Email email = new Email();
//                email.setRecipient(panne.getEmailDeclarant());
//                email.setSubject("Alerte: Durée de la panne dépassée");
//
//                StringBuilder emailBody = new StringBuilder();
//                emailBody.append("Bonjour Declarant,\n\n")
//                        .append("La panne suivante a dépassé 15 jours de durée :\n\n")
//                        .append("   - Nom du phare : ")
//                        .append(panne.getFeu().getNomLocalisation()).append(panne.getFeu().getPosition()).append("\n")
//                        .append("   - Région : ").append(panne.getRegion().getNomRegion()).append("\n")
//                        .append("   - Province : ").append(panne.getProvince().getNomProvince()).append("\n")
//                        .append("   - Date de la panne : ").append(panne.getDatePanne()).append("\n")
//                        .append("   - Durée de la panne : ").append(daysSincePanne).append(" jours\n\n")
//                        .append("Veuillez prendre les mesures nécessaires.\n\n")
//                        .append("Cordialement,\n")
//                        .append("L'équipe de gestion des phares");
//
//                email.setBody(emailBody.toString());
//                String result = emailService.sendMail(email);
//                if (!result.equals("Email sent")) {
//                    throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'alerte par e-mail.");
//                }
//            }
//        }
//    }

    @Override
    //@Scheduled(fixedDelay = 15 * 24 * 60 * 60 * 1000) // Exécuter toutes les 15 jours
    //@Scheduled(fixedRate = 60000) // Exécuter toutes les 60 secondes (ajustez selon vos besoins)
    public void suivrePannes() {
        List<Panne> pannes = panneRepository.findByEtatGeneral("hors service");

        for (Panne panne : pannes) {
            LocalDateTime now = LocalDateTime.now();
            LocalDate datePanne = panne.getDatePanne();
            LocalDateTime dateTimePanne = datePanne.atStartOfDay(); // Convert LocalDate to LocalDateTime

            Duration duration = Duration.between(dateTimePanne, now);
            long minutesSincePanne = duration.toMinutes();

            if (minutesSincePanne > 10) {
                // Send email only if the duration exceeds 2 minutes
                Email email = new Email();
                email.setRecipient(panne.getEmailDeclarant());
                email.setSubject("Alerte: Durée de la panne dépassée");

                StringBuilder emailBody = new StringBuilder();
                emailBody.append("Bonjour Declarant,\n\n")
                        .append("La panne suivante a dépassé 2 minutes de durée :\n\n")
                        .append("   - Nom du phare : ")
                        .append(panne.getFeu().getNomLocalisation()).append(panne.getFeu().getPosition()).append("\n")
                        .append("   - Région : ").append(panne.getRegion().getNomRegion()).append("\n")
                        .append("   - Province : ").append(panne.getProvince().getNomProvince()).append("\n")
                        .append("   - Date de la panne : ").append(panne.getDatePanne()).append("\n")
                        .append("   - Durée de la panne : ").append(minutesSincePanne).append(" minutes\n\n")
                        .append("Veuillez prendre les mesures nécessaires.\n\n")
                        .append("Cordialement,\n")
                        .append("L'équipe de gestion des phares");

                email.setBody(emailBody.toString());
                String result = emailService.sendMail(email);
                if (!result.equals("Email sent")) {
                    throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'alerte par e-mail.");
                }
            }
        }
    }



    @Override
    // Tâche planifiée pour incrémenter le temps hors service toutes les heures
    //@Scheduled(cron = "0 0 * * * *")
    @Scheduled(cron = "0 * * * * *") // Exécution chaque minute

    public void incrementOutOfServiceTimeForAllPannes() {
        List<Panne> pannes = panneRepository.findAll();
        for (Panne panne : pannes) {
            panne.incrementOutOfServiceTime();
            panneRepository.save(panne);
        }
    }


    @Override
    public double calculerTauxDisponibilite(Integer id) {
        Panne panne = panneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Panne not found"));

        // Check if both dateDebutService and dateFinService are not null
        if (panne.getDateDebutService() == null || panne.getDatePanne() == null) {
            throw new RuntimeException("Cannot calculate availability rate: missing start or end service dates.");
        }

        LocalDateTime dateDebutService = panne.getDateDebutService().atStartOfDay();
        LocalDateTime dateFinService = panne.getDatePanne().atStartOfDay();
        Double outOfServiceTime = panne.getOutOfServiceTime();

        Duration serviceDuration = Duration.between(dateDebutService, dateFinService);
        Double totalOutOfServiceTime = outOfServiceTime;

        double tauxDiso = ((Double) (serviceDuration.toMinutes() - totalOutOfServiceTime) / serviceDuration.toMinutes()) * 100;

        return tauxDiso;
    }

    @Override
    @Transactional
    public Panne updateAvisPath(Integer id, String avisPath) {
        Panne panne = panneRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Panne not found with id: " + id));
        panne.setAvisAuNavPdf(avisPath);
        return panneRepository.save(panne);
    }



//    @Override
//    public double calculerTauxDisponibilite(Integer id) {
//        Panne panne = panneRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Panne not found"));
//
//        // Vérifiez si dateDebutService et dateFinService ne sont pas nuls
//        if (panne.getDateDebutService() == null || panne.getDatePanne() == null) {
//            throw new RuntimeException("Cannot calculate availability rate: missing start or end service dates.");
//        }
//
//        LocalDateTime dateDebutService = panne.getDateDebutService().atStartOfDay();
//        LocalDateTime dateFinService = panne.getDatePanne().atStartOfDay();
//        Double outOfServiceTimeInDays = panne.getOutOfServiceTime() / 24.0; // Convert outOfServiceTime from hours to days
//
//        Duration serviceDuration = Duration.between(dateDebutService, dateFinService);
//        Double totalOutOfServiceTime = outOfServiceTimeInDays; // Use outOfServiceTime in days
//
//        double availabilityRate = ((Double) (serviceDuration.toDays() - totalOutOfServiceTime) / serviceDuration.toDays()) * 100;
//
//        return availabilityRate;
//    }


}
