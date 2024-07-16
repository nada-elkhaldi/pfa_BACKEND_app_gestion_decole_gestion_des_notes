package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.model.Budget;
import com.example.mySpringProject.model.Province;
import com.example.mySpringProject.model.Region;
import com.example.mySpringProject.model.User;
import com.example.mySpringProject.repository.BudgetRepository;
import com.example.mySpringProject.repository.ProvinceRepository;
import com.example.mySpringProject.repository.RegionRepository;
import com.example.mySpringProject.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BudgetServiceImpl implements BudgetService {

    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetServiceImpl(ProvinceRepository provinceRepository, RegionRepository regionRepository, BudgetRepository budgetRepository) {
        this.provinceRepository = provinceRepository;
        this.regionRepository = regionRepository;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Budget addBudget(Budget request) {
       Budget budget = new Budget();

        Region region = regionRepository.findById(request.getRegion().getId())
                .orElseThrow(() -> new RuntimeException("Region not found"));
        Province province = provinceRepository.findById(request.getProvince().getId())
                .orElseThrow(() -> new RuntimeException("Province not found"));
        budget.setAnnee(request.getAnnee());
        budget.setBudget(request.getBudget());
        budget.setRegion(region);
        budget.setProvince(province);
        budget.setPrevisionOperation(request.getPrevisionOperation());
      Budget savedBudget= budgetRepository.save(budget);

      return savedBudget;
    }

    @Override
    public List<Budget> getAllBudgets() {
        List<Budget> budgets = budgetRepository.findAll();
        return budgets.stream().map((budget)-> budget).collect(Collectors.toList());
    }
}
