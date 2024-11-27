package com.example.mySpringProject.repository;

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

    @Query("SELECT u FROM User u WHERE u.role.roleName IN ('DPDPM', 'DPDPM-User') ")
    List<User> getAllDPDPMUsers();

    @Query("SELECT u FROM User u WHERE u.role.roleName IN ('DHOC', 'DHOC-User')")
    List<User> getAllDHOCUsers();

    @Query("SELECT u FROM User u WHERE u.role.roleName IN ('DPE', 'DPE-User')")
    List<User> getAllDPEUsers();

    @Query("SELECT u FROM User u WHERE u.role.roleName IN ('AutoPort', 'AutoPort-User') ")
    List<User> getAllAutoPortUsers();


    @Query("SELECT u FROM User u WHERE u.role.roleName IN ('DRE', 'DRE-User') ")
    List<User> getAllDREUsers();


    User findById(int id);

    User save(User user);


    @Query("SELECT u FROM User u WHERE u.role.roleName IN :roleNames")
    List<User> findByRole_NameIn(List<String> roleNames);


    List<User> findByActive(boolean active);


    long count();

}
