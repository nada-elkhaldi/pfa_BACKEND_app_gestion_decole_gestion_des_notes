package com.example.mySpringProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity

@Table(name= "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name="id")
     private Integer id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="region")
    private String region;

    @Column(name="organisme")
    private String organisme;


    @Column(name="email")
    private String email;


    @Column(name="password")
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;








    public User(Integer id, String firstName, String lastName, String email, String region, String organisme, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.region = region;
        this.organisme = organisme;
        this.password = password;
        this.role = role;
    }

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
