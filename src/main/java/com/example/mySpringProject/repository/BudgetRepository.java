package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

}
