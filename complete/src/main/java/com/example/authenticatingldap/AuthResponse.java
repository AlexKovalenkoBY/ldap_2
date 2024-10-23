package com.example.authenticatingldap;

public class AuthResponse {
    private final String jwt;

    // Конструктор с параметром
    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    // Геттер
    public String getJwt() {
        return jwt;
    }
}