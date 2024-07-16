package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}
