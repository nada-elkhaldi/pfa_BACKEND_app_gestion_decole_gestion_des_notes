package com.example.mySpringProject.repository;

import com.example.mySpringProject.model.Role;
import com.example.mySpringProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = 'DPDPM'")
    List<User> getAllDPDPMUsers();

    @Query("SELECT u FROM User u WHERE u.role = 'DHOC'")
    List<User> getAllDHOCUsers();

    @Query("SELECT u FROM User u WHERE u.role = 'DPE'")
    List<User> getAllDPEUsers();

    @Query("SELECT u FROM User u WHERE u.role = 'AutoPort'")
    List<User> getAllAutoPortUsers();

    User findById(int id);

    User save(User user);


}
