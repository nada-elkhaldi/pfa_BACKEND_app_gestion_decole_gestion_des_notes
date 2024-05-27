package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Classe;
import com.example.mySpringProject.model.Groupe;
import com.example.mySpringProject.model.Role;
import com.example.mySpringProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);



    @Query("SELECT u FROM User u WHERE u.role = 'ETUDIANT'")
    List<User> getAllEtudiants();

    @Query("SELECT u FROM User u WHERE u.role = 'ENSEIGNANT'")
    List<User> getAllEnseignants();

    User findByIdAndRole(int id, Role role);

    User save(User user);

    List<User> findByClasseIdAndGroupeId(Integer classeId, Integer groupeId);
}
