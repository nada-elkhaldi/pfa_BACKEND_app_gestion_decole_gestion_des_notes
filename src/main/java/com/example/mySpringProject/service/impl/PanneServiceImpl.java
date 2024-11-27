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

import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
    public PanneDto addPanne(PanneDto panneDto, Integer userId, LocalDate startDate, LocalDate endDate) {

        if (panneDto.getIdFeu() == null ||  panneDto.getIdProvince() == null) {
            throw new IllegalArgumentException("Les identifiants ne doivent pas être nulls");
        }

        System.out.println("ID Feu: " + panneDto.getIdFeu());

        System.out.println("ID Province: " + panneDto.getIdProvince());

        Panne newPanne = PanneMapper.mapToPanne(panneDto);

        Feu feu = feuRepository.findById(panneDto.getIdFeu())
                .orElseThrow(() -> new RuntimeException("Feu not found"));

        Province province = provinceRepository.findById(panneDto.getIdProvince())
                .orElseThrow(() -> new RuntimeException("Province not found"));

        newPanne.setFeu(feu);

        newPanne.setProvince(province);

        User declarant = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        newPanne.setEmailDeclarant(declarant.getEmail());
        Panne savedPanne = panneRepository.save(newPanne);

        feu.setEtatFonctionnement("Hors service");
        feuRepository.save(feu);

        Declaration declaration = new Declaration();
        declaration.setTyeDeclaration("Panne");
        declaration.setEtat("En attente..");
        declaration.setDateDeclaration(LocalDate.now());
        declaration.setPanne(savedPanne);
        declaration.setDeclarant(declarant);
        declarationRepository.save(declaration);


        List<String> roles = Arrays.asList("DPDPM", "DPDPM-User");
        List<User> dpdpmUsers = userRepository.findByRole_NameIn(roles);



        for (User user : dpdpmUsers) {
            Email email = new Email();
            email.setRecipient(user.getEmail());
            email.setSubject("Notification de panne de phare");

            StringBuilder emailBody = new StringBuilder();
            emailBody
                    .append("<p>Bonjour DPDPM,</p>")
                    .append("<p>Nous vous informons qu'une panne a été déclarée pour le phare suivant :</p>")
                    .append("<ul>")
                    .append("<li><strong>Nom du phare :</strong> ").append(savedPanne.getFeu().getNomLocalisation()).append(" ").append(savedPanne.getFeu().getPosition()).append("</li>")
                    .append("<li><strong>Région :</strong> ").append(savedPanne.getProvince().getRegion().getNomRegion()).append("</li>")
                    .append("<li><strong>Province :</strong> ").append(savedPanne.getProvince().getNomProvince()).append("</li>")
                    .append("<li><strong>Date de la panne :</strong> ").append(savedPanne.getDatePanne()).append("</li>")
                    .append("<li><strong>État de fonctionnement des feux de secours :</strong> ").append(savedPanne.getEtatFonctionnementDeFeuDeSecours()).append("</li>")
                    .append("</ul>")
                    .append("<p>Veuillez prendre les mesures nécessaires pour résoudre ce problème.</p>")
                    .append("<p>Cordialement,<br/>")
                    .append("L'équipe de gestion des phares</p>");


            email.setBody(emailBody.toString());
            String result = emailService.sendMail(email);
            if (!result.equals("Email sent")) {
                throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
            }
        }

        tauxDisponibiliteService.mettreAJourTauxDisponibilitePourTousLesPhares(startDate, endDate);

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
        // Trouver la panne associée à l'ID
        Panne panne = panneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panne with id " + id + " not found"));

        // Vérifier et mettre à jour l'état de la panne
        if (!panne.getEtatGeneral().equals(request.getEtatGeneral())) {
            if (request.getEtatGeneral().equals("Remise en service")) {
                panne.stopOutOfServiceTime();
            }
        }

        // Trouver le feu associé à la panne
        Feu feu = feuRepository.findById(panne.getFeu().getId())
                .orElseThrow(() -> new RuntimeException("Feu not found"));
        feu.setEtatFonctionnement("Remise en service");
        feuRepository.save(feu);

        // Mettre à jour la panne avec les nouvelles informations
        panne.setArchive(1);
        panne.setEtatGeneral(request.getEtatGeneral());
        panne.setDateRemiseEnService(request.getDateRemiseEnService());
        Panne savedPanne = panneRepository.save(panne);

        Declaration declaration = new Declaration();
        declaration.setTyeDeclaration("Remise en service");
        declaration.setEtat("Traitée");
        declaration.setDateDeclaration(LocalDate.now());


        declarationRepository.save(declaration);

        // Mettre à jour l'état de la déclaration associée à la panne
        Declaration associatedDeclaration = declarationRepository.findByPanne(savedPanne);
        if (associatedDeclaration != null) {
            associatedDeclaration.setEtat("Traitée");
            declarationRepository.save(associatedDeclaration);
        } else {
            throw new RuntimeException("La déclaration associée à la panne n'a pas été trouvée.");
        }

        List<String> roles = Arrays.asList("DPDPM", "DPDPM-User");
        List<User> dpdpmUsers = userRepository.findByRole_NameIn(roles);

        for (User user : dpdpmUsers) {
            Email email = new Email();
            email.setRecipient(user.getEmail());
            email.setSubject("Notification de panne de phare");

            StringBuilder emailBody = new StringBuilder();

            emailBody.append("<p>Bonjour DPDPM,</p>")
                    .append("<p>Nous vous informons qu'une réparation a été effectuée pour le phare suivant :</p>")
                    .append("<ul>")
                    .append("<li><strong>Nom du phare :</strong> ").append(savedPanne.getFeu().getNomLocalisation()).append(" ").append(savedPanne.getFeu().getPosition()).append("</li>")
                    .append("<li><strong>Région :</strong> ").append(savedPanne.getProvince().getRegion().getNomRegion()).append("</li>")
                    .append("<li><strong>Province :</strong> ").append(savedPanne.getProvince().getNomProvince()).append("</li>")
                    .append("<li><strong>Date de début de la réparation :</strong> ").append(savedPanne.getDatePanne()).append("</li>")
                    .append("<li><strong>Date de remise en service :</strong> ").append(savedPanne.getDateRemiseEnService()).append("</li>")
                    .append("</ul>")
                    .append("<p>Le phare est maintenant de nouveau opérationnel. Nous vous remercions pour votre attention.</p>")
                    .append("<p>Cordialement,<br/>")
                    .append("L'équipe de gestion des phares</p>");


            email.setBody(emailBody.toString());
            String result = emailService.sendMail(email);
            if (!result.equals("Email sent")) {
                throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
            }
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
    public Panne validatePanne(Integer id) {
        Panne panne = panneRepository.findById(id).orElseThrow(() -> new RuntimeException("Panne not found"));
        panne.setTraitee(1);
        panne.incrementOutOfServiceTime();
        panneRepository.save(panne);

        List<String> roles = Arrays.asList("DHOC", "DHOC-User");
        List<User> dhocUsers = userRepository.findByRole_NameIn(roles);

        for (User user : dhocUsers) {
            Email email = new Email();
            email.setRecipient(user.getEmail());
            email.setSubject("Notification de panne de phare");

            StringBuilder emailBody = new StringBuilder();
            emailBody.append("<p>Bonjour DHOC,</p>")
                    .append("<p>Nous vous informons qu'une panne a été déclarée et validée pour le phare suivant :</p>")
                    .append("<ul>")
                    .append("   <li><strong>Nom du phare :</strong> ").append(panne.getFeu().getNomLocalisation()).append(" -- ").append(panne.getFeu().getPosition()).append("</li>")
                    .append("   <li><strong>Région :</strong> ").append(panne.getProvince().getRegion().getNomRegion()).append("</li>")
                    .append("   <li><strong>Province :</strong> ").append(panne.getProvince().getNomProvince()).append("</li>")
                    .append("   <li><strong>Date de la panne :</strong> ").append(panne.getDatePanne()).append("</li>")
                    .append("   <li><strong>État de fonctionnement des feux de secours :</strong> ").append(panne.getEtatFonctionnementDeFeuDeSecours()).append("</li>")
                    .append("</ul>")
                    .append("<p>Veuillez prendre les mesures nécessaires pour résoudre ce problème.</p>")
                    .append("<p>Cordialement,<br/>")
                    .append("L'équipe de gestion des phares</p>");


            email.setBody(emailBody.toString());
            String result = emailService.sendMail(email);
            if (!result.equals("Email sent")) {
                throw new RuntimeException("Une erreur s'est produite lors de l'envoi de l'e-mail.");
            }
        }

        return panne;
    }



    @Override
    @Scheduled(fixedDelay = 15 * 24 * 60 * 60 * 1000) // Exécuter toutes les 15 jours
    //@Scheduled(fixedRate = 60000)  Pour le test
    public void suivrePannes() {
        List<Panne> pannes = panneRepository.findByEtatGeneral("Hors service");

        for (Panne panne : pannes) {
            LocalDate now = LocalDate.now();
          Period period = Period.between(panne.getDatePanne(), now);
           long daysSincePanne = period.getDays();
            if (daysSincePanne > 15) {

                Email email = new Email();
                email.setRecipient(panne.getEmailDeclarant());
                email.setSubject("Alerte: Durée de la panne dépassée");

                StringBuilder emailBody = new StringBuilder();
                emailBody.append("<p>Bonjour Declarant,</p>")
                        .append("<p>La panne suivante a dépassé 15 jours de durée :</p>")
                        .append("<ul>")
                        .append("<li><strong>Nom du phare :</strong> ").append(panne.getFeu().getNomLocalisation()).append(" ").append(panne.getFeu().getPosition()).append("</li>")
                        .append("<li><strong>Région :</strong> ").append(panne.getProvince().getRegion().getNomRegion()).append("</li>")
                        .append("<li><strong>Province :</strong> ").append(panne.getProvince().getNomProvince()).append("</li>")
                        .append("<li><strong>Date de la panne :</strong> ").append(panne.getDatePanne()).append("</li>")
                        .append("<li><strong>Durée de la panne :</strong> ").append(daysSincePanne).append(" minutes</li>")
                        .append("</ul>")
                        .append("<p>Veuillez prendre les mesures nécessaires.</p>")
                        .append("<p>Cordialement,<br/>")
                        .append("L'équipe de gestion des phares</p>");

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
   // @Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 0 * * * *")
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
            Table table = new Table(UnitValue.createPercentArray(new float[]{2,2,3,2,3,2,2}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Définir les colonnes
            table.addHeaderCell("Phare");
            table.addHeaderCell("Province");
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
    public List<Panne> getAllPannesPasTraitee() {
        List<Panne> pannes = panneRepository.findByTraitee(0);
        return pannes.stream().map((panne)-> panne).collect(Collectors.toList());
    }

    @Override
    public List<Panne> getAllPannesTraitee() {
        List<Panne> pannes = panneRepository.findByTraitee(1);
        return pannes.stream().map((panne)-> panne).collect(Collectors.toList());
    }

    @Override
    public List<Panne> getAllPannesArchivee() {
        List<Panne> pannes = panneRepository.findByArchive(1);
        return pannes.stream().map((panne)-> panne).collect(Collectors.toList());
    }

    @Override
    public long getPannesCount() {
        return panneRepository.count();
    }

    @Override
    public long getNonTraiteesCount() {
        return panneRepository.countByTraitee(0);
    }


    public List<Panne> findByIds(List<Integer> ids) {
        return panneRepository.findAllById(ids);
    }

}
