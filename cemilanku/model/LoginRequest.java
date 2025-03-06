package com.cemilanku.model;

import jakarta.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull
    private String email;
    @NotNull
    private String kataSandi;
    @NotNull
    private Roles role;

    public LoginRequest(String email, String kataSandi, Roles role) {
        this.email = email;
        this.kataSandi = kataSandi;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getKataSandi() {
        return kataSandi;
    }
    public void setKataSandi(String kataSandi) {
        this.kataSandi = kataSandi;
    }
    public Roles getRole() {
        return role;
    }
    public void setRole(Roles role) {
        this.role = role;
    }
}
