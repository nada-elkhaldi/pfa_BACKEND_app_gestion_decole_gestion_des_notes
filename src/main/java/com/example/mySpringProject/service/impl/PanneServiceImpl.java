package com.example.mySpringProject.service.impl;


import com.example.mySpringProject.dto.PanneDto;
import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.mapper.PanneMapper;
import com.example.mySpringProject.model.*;
import com.example.mySpringProject.repository.*;
import com.example.mySpringProject.service.PanneService;
import com.example.mySpringProject.service.TauxDisponibiliteService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
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
    private final UserRepository userRepository;
    private final DeclarationRepository declarationRepository;
    private final TauxDisponibiliteService tauxDisponibiliteService;

    @Autowired
    public PanneServiceImpl(PanneRepository panneRepository, EmailService emailService, FeuRepository feuRepository, ProvinceRepository provinceRepository, RegionRepository regionRepository, PanneMapper panneMapper, UserRepository userRepository, DeclarationRepository declarationRepository, TauxDisponibiliteService tauxDisponibiliteService) {

        this.emailService = emailService;
        this.panneRepository = panneRepository;
        this.feuRepository = feuRepository;
        this.provinceRepository = provinceRepository;
        this.regionRepository = regionRepository;
        this.panneMapper = panneMapper;
        this.userRepository = userRepository;
        this.declarationRepository = declarationRepository;
        this.tauxDisponibiliteService = tauxDisponibiliteService;
    }

    @Override
    public PanneDto addPanne(PanneDto panneDto, Integer userId) {

        if (panneDto.getIdFeu() == null || panneDto.getIdRegion() == null || panneDto.getIdProvince() == null) {
            throw new IllegalArgumentException("Les identifiants ne doivent pas être nulls");
        }

        System.out.println("ID Feu: " + panneDto.getIdFeu());
        System.out.println("ID Région: " + panneDto.getIdRegion());
        System.out.println("ID Province: " + panneDto.getIdProvince());

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


        User declarant = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        newPanne.setEmailDeclarant(declarant.getEmail());
        Panne savedPanne = panneRepository.save(newPanne);

        //
        feu.setEtatFonctionnement("Hors service");
        feuRepository.save(feu);
        //
        Declaration declaration = new Declaration();
        declaration.setTyeDeclaration("Panne");
        declaration.setDateDeclaration(LocalDate.now());
        declaration.setPanne(savedPanne);
        declaration.setDeclarant(declarant);
        declarationRepository.save(declaration);

        // Préparation de l'email
        Email email = new Email();
        email.setRecipient("elkhaldinada05@gmail.com");
        email.setSubject("Notification de panne de phare");

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Bonjour DPDPM,\n\n")
                .append("Nous vous informons qu'une panne a été déclarée pour le phare suivant :\n\n")
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

        //

        // Appeler le service pour mettre à jour le taux de disponibilité pour le phare associé
        tauxDisponibiliteService.mettreAJourTauxDisponibilite(savedPanne.getFeu().getId());

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

        panne.setMotifDePanne(request.getMotifDePanne());
        panne.setPlanDAction(request.getPlanDAction());
        panne.setPrevisionDeResolution(request.getPrevisionDeResolution());


        Panne savedPanne = panneRepository.save(panne);
        return savedPanne;
    }

    @Override
    public Panne annoncerLaReparation(Integer id, Panne request) {
        Panne panne = panneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panne with id " + id + " not found"));
        if (!panne.getEtatGeneral().equals(request.getEtatGeneral())) {
            if (request.getEtatGeneral().equals("Remise en service")) {
                panne.stopOutOfServiceTime();
            }
        }

        Feu feu = feuRepository.findById(panne.getFeu().getId())
                .orElseThrow(() -> new RuntimeException("Feu not found"));
        feu.setEtatFonctionnement("Remise en service");
        feuRepository.save(feu);

        panne.setArchive(1);
        panne.setEtatGeneral(request.getEtatGeneral());
        panne.setDateRemiseEnService(request.getDateRemiseEnService());
        Panne savedPanne = panneRepository.save(panne);

        Declaration declaration = new Declaration();
        declaration.setTyeDeclaration("Remise en service");
        declaration.setDateDeclaration(LocalDate.now());
        declaration.setPanne(savedPanne);
        declarationRepository.save(declaration);

        // Préparation de l'email
        Email email = new Email();
        email.setRecipient("elkhaldinada05@gmail.com"); // emaildpdpm
        email.setSubject("Notification de réparation et de remise en service de phare");

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Bonjour DPDPM,\n\n")
                .append("Nous vous informons qu'une réparation a été effectuée pour le phare suivant :\n\n")
                .append("   - Nom du phare : ")
                .append(savedPanne.getFeu().getNomLocalisation()).append(savedPanne.getFeu().getPosition()).append("\n")
                .append("   - Région : ").append(savedPanne.getRegion().getNomRegion()).append("\n")
                .append("   - Province : ").append(savedPanne.getProvince().getNomProvince()).append("\n")
                .append("   - Date de début de la réparation : ").append(savedPanne.getDatePanne()).append("\n")
                .append("   - Date de remise en service : ").append(savedPanne.getDateRemiseEnService()).append("\n")
                .append("Le phare est maintenant de nouveau opérationnel. Nous vous remercions pour votre attention.\n\n")
                .append("Cordialement,\n")
                .append("L'équipe de gestion des phares");

        email.setBody(emailBody.toString());
        String result = emailService.sendMail(email);
        if (!result.equals("Email sent")) {
            throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
        }
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
    public Panne validatePanne(Integer id, String emailDHOC) {
        Panne panne = panneRepository.findById(id).orElseThrow(() -> new RuntimeException("Panne not found"));
        panne.setTraitee(1);
        panne.incrementOutOfServiceTime();
        panneRepository.save(panne);

        Email email = new Email();
        email.setRecipient("elkhaldinada7@gmail.com");
        email.setSubject("Notification de panne de phare");

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Bonjour DHOC,\n\n")
                .append("Nous vous informons qu'une panne a été déclarée et validée pour le phare suivant :\n\n")
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
        List<Panne> pannes = panneRepository.findByEtatGeneral("Hors service");

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
    @Transactional
    public Panne updateAvisPath(Integer id, String avisPath) {
        Panne panne = panneRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Panne not found with id: " + id));
        panne.setAvisAuNavPdf(avisPath);
        return panneRepository.save(panne);
    }


    @Override
    public List<Panne> getPannesByRegion(Integer regionId) {
        return panneRepository.findPannesByRegionId(regionId);
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


    public byte[] generatePanneReport(List<Panne> pannes) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument, PageSize.A4);

        try {
            Paragraph title = new Paragraph("Rapport des Pannes")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Introduction
            Paragraph introduction = new Paragraph("Ce rapport présente une vue d'ensemble des pannes enregistrées, y compris les détails de chaque panne, les temps d'indisponibilité, et les taux de disponibilité. Les données sont fournies pour une analyse approfondie des incidents survenus.")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(12)
                    .setMarginBottom(20);
            document.add(introduction);

            // Créer un tableau
            Table table = new Table(UnitValue.createPercentArray(new float[]{2,2,2,3,2,3,2,2}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Définir les colonnes
            table.addHeaderCell("Phare");
            table.addHeaderCell("Province");
            table.addHeaderCell("Nature de la Panne");
            table.addHeaderCell("Date de Panne");
            table.addHeaderCell("État Général");
            table.addHeaderCell("Date Remise en Service");
            table.addHeaderCell("Temps Hors Service");
            table.addHeaderCell("Taux de Disponibilité");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            // Ajouter les données
            for (Panne panne : pannes) {
                table.addCell(panne.getFeu().getNomLocalisation() != null ? panne.getFeu().getNomLocalisation() : "");
                table.addCell(panne.getProvince().getNomProvince() != null ? panne.getProvince().getNomProvince() : "");
                table.addCell(panne.getNatureDePanne() != null ? panne.getNatureDePanne() : "");
                table.addCell(panne.getDatePanne() != null ? panne.getDatePanne().format(formatter) : "");
                table.addCell(panne.getEtatGeneral() != null ? panne.getEtatGeneral() : "");
                table.addCell(panne.getDateRemiseEnService() != null ? panne.getDateRemiseEnService().format(formatter) : "");

                // convertir en entier
                double outOfServiceTimeDouble = panne.getOutOfServiceTime();
                int outOfServiceTimeInt = (int) Math.round(outOfServiceTimeDouble);
                table.addCell(String.valueOf(outOfServiceTimeInt));

                // récupérer et ajouter le taux de disponibilité
                double tauxDeDisponibilite = tauxDisponibiliteService.getTauxDisponibiliteForFeu(panne.getFeu());
                table.addCell(String.format("%.2f", tauxDeDisponibilite));
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return byteArrayOutputStream.toByteArray();
    }


    @Override
    public List<Panne> getAllPannesTraitee() {
        List<Panne> pannes = panneRepository.findByTraitee(0);
        return pannes.stream().map((panne)-> panne).collect(Collectors.toList());
    }

    @Override
    public List<Panne> getAllPannesArchivee() {
        List<Panne> pannes = panneRepository.findByArchive(1);
        return pannes.stream().map((panne)-> panne).collect(Collectors.toList());
    }


    public List<Panne> findByIds(List<Integer> ids) {
        return panneRepository.findAllById(ids);
    }

}
