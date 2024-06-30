package com.example.mySpringProject.model;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    // Getter and Setter
    private String newPassword;



    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

