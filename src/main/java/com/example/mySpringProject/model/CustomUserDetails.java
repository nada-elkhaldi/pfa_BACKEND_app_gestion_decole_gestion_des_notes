package com.example.mySpringProject.model;

public class CustomUserDetails {
    private final String email;
    private final String role;

    public CustomUserDetails(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public boolean hasRole(String roleToCheck) {
        return this.role.equals(roleToCheck);
    }
}
