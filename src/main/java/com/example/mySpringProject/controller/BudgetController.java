package com.example.mySpringProject.controller;


import com.example.mySpringProject.model.Budget;
import com.example.mySpringProject.service.BudgetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api6",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
public class BudgetController {
    private final BudgetService budgetService;
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/addBudget")
    public ResponseEntity<Budget> addBudget(@RequestBody Budget budget) {
        Budget savedBudget= budgetService.addBudget(budget);
        return ResponseEntity.ok(savedBudget);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/budgets")
    public ResponseEntity<List<Budget>> getAllBudgets() {
        List<Budget> budgets = budgetService.getAllBudgets();
        return  ResponseEntity.ok(budgets);
    }
}
