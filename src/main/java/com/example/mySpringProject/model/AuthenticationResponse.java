package com.example.mySpringProject.model;

import com.example.mySpringProject.service.AuthenticationService;

public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

}
