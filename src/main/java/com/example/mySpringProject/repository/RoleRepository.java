package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
