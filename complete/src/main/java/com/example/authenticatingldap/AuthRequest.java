package com.example.authenticatingldap;

public class AuthRequest {
    private String username;
    private String password;

    // Конструктор по умолчанию для десериализации
    public AuthRequest() {}

    // Конструктор с параметрами
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттеры и сеттеры
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}