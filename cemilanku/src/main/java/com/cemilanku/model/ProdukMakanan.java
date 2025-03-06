package com.cemilanku.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "produkMakanan")
public class ProdukMakanan {
    @Id
    private String id;
    @NotNull
    @NotBlank
    @Size(max = 30, message = "maksimal 30 huruf!")
    private String namaProduk;
    @NotNull
    private JenisMakanan jenisMakanan;
    @NotNull
    @NotBlank
    @Size(max = 100, message = "maksimal 50 huruf!")
    private String deskripsi;
    @NotNull
    @NotBlank
    @Min(value = 10000, message = "minimal 10000")
    private int hargaProduk;
    @NotNull
    @NotBlank
    @Max(value = 100, message = "maksimal 100 stok!")
    private int stokProduk;
    @CreatedDate
    private LocalDateTime produkRilis;
    @NotNull
    private String imageUrl;
    @Version
    private int version;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNamaProduk() {
        return namaProduk;
    }
    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }
    public JenisMakanan getJenisMakanan() {
        return jenisMakanan;
    }
    public void setJenisMakanan(JenisMakanan jenisMakanan) {
        this.jenisMakanan = jenisMakanan;
    }
    public String getDeskripsi() {
        return deskripsi;
    }
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    public int getHargaProduk() {
        return hargaProduk;
    }
    public void setHargaProduk(int hargaProduk) {
        this.hargaProduk = hargaProduk;
    }
    public int getStokProduk() {
        return stokProduk;
    }
    public void setStokProduk(int stokProduk) {
        this.stokProduk = stokProduk;
    }
    public LocalDateTime getProdukRilis() {
        return produkRilis;
    }
    public void setProdukRilis(LocalDateTime produkRilis) {
        this.produkRilis = produkRilis;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
}