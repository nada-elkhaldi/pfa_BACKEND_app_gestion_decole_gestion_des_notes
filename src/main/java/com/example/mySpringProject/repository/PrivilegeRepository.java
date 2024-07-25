package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
    Privilege findByName(String name);
}
