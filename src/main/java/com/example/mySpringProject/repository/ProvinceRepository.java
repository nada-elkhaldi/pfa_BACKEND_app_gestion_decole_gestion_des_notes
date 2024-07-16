package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Panne;
import com.example.mySpringProject.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {


}
