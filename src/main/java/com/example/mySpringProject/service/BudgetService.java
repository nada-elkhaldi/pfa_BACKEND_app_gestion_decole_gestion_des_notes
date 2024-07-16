package com.example.mySpringProject.service;

import com.example.mySpringProject.model.Budget;

import java.util.List;

public interface BudgetService {

    Budget addBudget(Budget budget);
    List<Budget> getAllBudgets();
}

