package com.example.mySpringProject.controller;


import com.example.mySpringProject.dto.CreditDto;
import com.example.mySpringProject.model.Credit;
import com.example.mySpringProject.model.HistoriqueCredit;
import com.example.mySpringProject.service.CreditService;
import com.example.mySpringProject.service.HistoriqueCreditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api7",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
public class CreditController {

    private CreditService creditService;

    private HistoriqueCreditService historiqueCreditService;

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/demander-un-credit")
    public ResponseEntity<CreditDto> demanderCredit(@RequestBody CreditDto creditDto) {
        CreditDto savedCredit = creditService.demanderCredit(creditDto);
        return new ResponseEntity<>(savedCredit, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/credits")
    public ResponseEntity<List<CreditDto>> getCredits() {
        List<CreditDto> credits = creditService.getCredits();
        return new ResponseEntity<>(credits, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @DeleteMapping("/deleteCredit/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable Integer id) {
        creditService.deleteCredit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/credit/{id}")
    public ResponseEntity<Credit> getCreditById(@PathVariable Integer id) {
        Credit credit = creditService.getCreditById(id);
        return new ResponseEntity<>(credit, HttpStatus.OK);
    }


    //historique credit
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/delegate")
    public void delegateCredit(@RequestParam Integer idDemande, @RequestParam Integer idBudget) {
        historiqueCreditService.delegateCredit(idDemande, idBudget);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/historique-credits")
    public ResponseEntity<List<HistoriqueCredit>> allCredits() {
        List<HistoriqueCredit> credits = historiqueCreditService.getAllHistoriqueCredit();
        return new ResponseEntity<>(credits, HttpStatus.OK);
    }
}
