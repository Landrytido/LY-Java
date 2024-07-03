package com.comptaapp.dto;

public class UserLoginDto {
    private String username;
    private String password;

    // Default constructor
    public UserLoginDto() {
    }

    // Constructor with parameters
    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
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
