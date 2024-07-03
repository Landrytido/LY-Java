package com.comptaapp.dto;

public class UserApprovalDto {
    private String username;
    private String roleName;

    public UserApprovalDto() {
    }

    public UserApprovalDto(String username, String roleName) {
        this.username = username;
        this.roleName = roleName;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}