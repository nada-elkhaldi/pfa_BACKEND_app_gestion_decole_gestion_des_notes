package com.example.mySpringProject.controller;


import com.example.mySpringProject.dto.CreditDto;
import com.example.mySpringProject.model.Credit;
import com.example.mySpringProject.service.CreditService;
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

    @PostMapping("/demander-un-credit")
    public ResponseEntity<CreditDto> demanderCredit(@RequestBody CreditDto creditDto) {
        CreditDto savedCredit = creditService.demanderCredit(creditDto);
        return new ResponseEntity<>(savedCredit, HttpStatus.CREATED);
    }

    @GetMapping("/credits")
    public ResponseEntity<List<CreditDto>> getCredits() {
        List<CreditDto> credits = creditService.getCredits();
        return new ResponseEntity<>(credits, HttpStatus.OK);
    }

    @GetMapping("/credit/{id}")
    public ResponseEntity<Credit> getCreditById(@PathVariable Integer id) {
        Credit credit = creditService.getCreditById(id);
        return new ResponseEntity<>(credit, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCredit/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable Integer id) {
        creditService.deleteCredit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/credit/{creditId}/deleguer")
    public ResponseEntity<Credit> delegateCredit(@PathVariable Integer creditId, @RequestParam Double montant) {
        Credit delegatedCredit = creditService.delegateCredit(creditId, montant);
        return new ResponseEntity<>(delegatedCredit, HttpStatus.OK);
    }
}
