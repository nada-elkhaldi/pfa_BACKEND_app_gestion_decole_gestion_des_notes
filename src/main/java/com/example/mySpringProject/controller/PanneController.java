package com.example.mySpringProject.controller;



import com.example.mySpringProject.dto.PanneDto;
import com.example.mySpringProject.model.Panne;

import com.example.mySpringProject.service.PanneService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api5",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})

public class PanneController {
    private final PanneService panneService;
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/addPanne")
    public ResponseEntity<PanneDto> addFeu(@RequestBody PanneDto panne) {
        PanneDto savedPanne= panneService.addPanne(panne);
        return ResponseEntity.ok(savedPanne);
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @GetMapping("/pannes")
    public ResponseEntity<List<PanneDto>> getAllPannes() {
        List<PanneDto> pannes = panneService.getAllPannes();
        return  ResponseEntity.ok(pannes);
    }

    @PutMapping("/updatePanne/{id}")
    public ResponseEntity<Panne> updatePanne(@PathVariable("id") Integer id, @RequestBody Panne updatedPanne) {
        Panne panne= panneService.updatePanne(id, updatedPanne);
        return ResponseEntity.ok(panne);
    }

    @DeleteMapping("/deletePanne/{id}")
    public ResponseEntity<String> deletePanne(@PathVariable("id") Integer id) {
        panneService.deletePanne(id);
        return ResponseEntity.ok("Panne deleted");

    }

    @PutMapping("/panne/validate/{id}")
    public Panne validatePanne(@PathVariable Integer id) {
        return panneService.validatePanne(id);
    }

    @PostMapping("/incrementOutOfServiceTime")
    public void incrementOutOfServiceTime() {
        panneService.incrementOutOfServiceTimeForAllPannes();
    }

    @GetMapping("/taux-disponibilite/{id}")
    public ResponseEntity<Double> calculerTauxDisponibilite(@PathVariable Integer id) {
        double tauxDisponibilite = panneService.calculerTauxDisponibilite(id);
        return ResponseEntity.ok(tauxDisponibilite);
    }

    @PostMapping("/uploadAvis/{id}")
    public ResponseEntity<String> uploadAvis(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.BAD_REQUEST);
        }

        try {
            // Enregistrer le fichier sur le serveur dans un dossier spécifique à la panne
            String fileName = saveFile(file, id);
            //
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




}
