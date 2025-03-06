package com.cemilanku.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "reviews")
public class Reviews {
    @Id
    private String id;
    @NotNull
    @NotBlank
    @Size(min = 6, max = 12, message = "minimal 6 ke 12 huruf!")
    private String nama;
    @NotNull
    @NotBlank
    @Size(min = 50, max = 100, message = "minimal 50 ke 100 huruf!")
    private String komentar;
    @NotNull
    @Max(value = 5, message = "Maksimal 5!")
    private int nilai;
    @Version
    private int version;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getKomentar() {
        return komentar;
    }
    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
    public int getNilai() {
        return nilai;
    }
    public void setNilai(int nilai) {
        this.nilai = nilai;
    }
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
}
