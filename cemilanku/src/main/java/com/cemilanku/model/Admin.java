package com.cemilanku.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "admin")
public class Admin {
    @Id
    private String id;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 12, message = "Minimal 6 ke 12 huruf!")
    private String nama;

    @NotNull
    @NotBlank
    @Size(max = 30, message = "Maksimal 30 huruf!")
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 12, message = "Minimal 6 ke 12 huruf!")
    private String kataSandi;

    private Roles role;

    @Version
    private int version;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getKataSandi() { return kataSandi; }
    public void setKataSandi(String kataSandi) { this.kataSandi = kataSandi; }
    public Roles getRole() { return role; }
    public void setRole(Roles role) { this.role = role; }
    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
}
