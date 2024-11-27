package com.example.mySpringProject.controller;



import com.example.mySpringProject.dto.PanneDto;
import com.example.mySpringProject.model.Panne;

import com.example.mySpringProject.model.TauDisposability;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.repository.PanneRepository;
import com.example.mySpringProject.service.PanneService;
import com.example.mySpringProject.service.TauxDisponibiliteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api5",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
public class PanneController {
    private final PanneService panneService;
    private final PanneRepository panneRepository;

    @Autowired
    private TauxDisponibiliteService tauxDisponibiliteService;

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/addPanne")
    public ResponseEntity<PanneDto> addPanne(
            @RequestBody PanneDto panneDto,
            @RequestParam Integer userId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            PanneDto savedPanne = panneService.addPanne(panneDto, userId, startDate, endDate);
            return ResponseEntity.ok(savedPanne);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/pannes")
    public ResponseEntity<List<PanneDto>> getAllPannes() {
        List<PanneDto> pannes = panneService.getAllPannes();
        return  ResponseEntity.ok(pannes);
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PutMapping("/updatePanne/{id}")
    public ResponseEntity<Panne> updatePanne(@PathVariable("id") Integer id, @RequestBody Panne updatedPanne) {
        Panne panne= panneService.updatePanne(id, updatedPanne);
        return ResponseEntity.ok(panne);
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PutMapping("/panneReparee/{id}")
    public ResponseEntity<Panne> reparerPanne(@PathVariable("id") Integer id, @RequestBody Panne updatedPanne) {
        Panne panne= panneService.annoncerLaReparation(id, updatedPanne);
        return ResponseEntity.ok(panne);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @DeleteMapping("/deletePanne/{id}")
    public ResponseEntity<String> deletePanne(@PathVariable("id") Integer id) {
        panneService.deletePanne(id);
        return ResponseEntity.ok("Panne deleted");

    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PutMapping("/panne/validate/{id}")
    public Panne validatePanne(@PathVariable Integer id) {

        return panneService.validatePanne(id);
    }


    @PostMapping("/incrementOutOfServiceTime")
    public void incrementOutOfServiceTime() {
        panneService.incrementOutOfServiceTimeForAllPannes();
    }

//
//    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//    @GetMapping("/taux-disponibilite/{id}")
//    public ResponseEntity<Panne> calculerTauxDisponibilite(
//            @PathVariable Integer id,
//            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
//        try {
//            double tauxDisponibilite = panneService.calculerTauxDisponibilite(id, startDate);
//            Panne updatedPanne = panneRepository.findById(id).orElseThrow(() -> new RuntimeException("Panne not found"));
//            return ResponseEntity.ok(updatedPanne);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }


    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/uploadAvis/{id}")
    public ResponseEntity<String> uploadAvis(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = saveFile(file, id);
            Panne updatedPanne = panneService.updateAvisPath(id, fileName);
            return new ResponseEntity<>("File uploaded successfully: " + fileName, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Could not upload the file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String saveFile(MultipartFile file, Integer id) throws IOException {
        String fileName = file.getOriginalFilename();
        // Définir le répertoire de téléchargement spécifique à la panne
        String uploadDir = "C:\\Users\\hp\\Desktop\\projectSMSM\\avis_au_navigateur\\" + id + "\\";
        File uploadDirFile = new File(uploadDir);

        // Créer le répertoire s'il n'existe pas
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
        // Créer le fichier de destination
        File dest = new File(uploadDir + fileName);
        file.transferTo(dest);
        return dest.getAbsolutePath();
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/avis/{id}")
    public ResponseEntity<Resource> getAvis(@PathVariable Integer id) {
        Panne panne = panneService.getPanneById(id);
        String filePath = panne.getAvisAuNavPdf();

        try {
            Path file = Paths.get(filePath);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/pannes/region/{regionId}")
    public List<Panne> getPannesParRegion(@PathVariable Integer regionId ) {
        return panneService.getPannesByRegion(regionId);
    }


    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/rapport-des-pannes")
    public ResponseEntity<byte[]> generatePanneReport(@RequestBody List<Panne> pannes) {
        // Log the received data
        for (Panne panne : pannes) {
            System.out.println("Received panne: " + panne);
        }

        byte[] pdfReport = panneService.generatePanneReport(pannes);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=panne_report.pdf");
        headers.set(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return new ResponseEntity<>(pdfReport, headers, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/pas-traitees")
    public ResponseEntity<List<Panne>> getPannesPasTraitees() {
        List<Panne> pannes = panneService.getAllPannesPasTraitee();
        return ResponseEntity.ok(pannes);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/pannes-traitees")
    public ResponseEntity<List<Panne>> getPannesTraitees() {
        List<Panne> pannes = panneService.getAllPannesTraitee();
        return ResponseEntity.ok(pannes);
    }



    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/archivees")
    public ResponseEntity<List<Panne>> getPannesArchivees() {
        List<Panne> pannes = panneService.getAllPannesArchivee();
        return ResponseEntity.ok(pannes);
    }

    //
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/taux-de-disponibilite")
    public ResponseEntity<String> mettreAJourTauxDisponibilite(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        try {
            tauxDisponibiliteService.mettreAJourTauxDisponibilitePourTousLesPhares(startDate, endDate);
            return ResponseEntity.ok("Taux de disponibilité mis à jour avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du taux de disponibilité.");
        }
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/ESMs/taux-de-disponibilite")
    public List<TauDisposability> obtenirTousLesTauxDisponibilite() {
        return tauxDisponibiliteService.obtenirTousLesTauxDisponibilite();
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/taux-disponibilite/region/{regionId}")
    public List<TauDisposability> getTauxDisponibiliteByRegionId(@PathVariable Integer regionId) {
        return tauxDisponibiliteService.getTauxDisponibiliteByRegionId(regionId);
    }


    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/pannes-count")
    public long getPannesCount() {
        return panneService.getPannesCount();
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/pannes-non-traitees/count")
    public long getNonTraiteesCount() {
        return panneService.getNonTraiteesCount();
    }
}
