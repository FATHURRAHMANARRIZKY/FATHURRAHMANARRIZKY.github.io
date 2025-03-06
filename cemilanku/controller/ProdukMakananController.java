package com.cemilanku.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cemilanku.model.ProdukMakanan;
import com.cemilanku.service.ProdukMakananService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produk-makanan")
public class ProdukMakananController {
    @Autowired
    private ProdukMakananService produkMakananService;

    // membuat produk makanan
    // @PostMapping
    // public ProdukMakanan postProdukMakanan(@Valid @RequestBody ProdukMakanan produkMakanan) {
    //     return produkMakananService.createProdukMakanan(produkMakanan);
    // }

    @PostMapping
    public ResponseEntity<ProdukMakanan> addProdukMakanan(
        @Valid
        @RequestParam("namaProduk") String namaProduk,
        @RequestParam("jenisMakanan") String jenisMakanan,
        // @RequestParam("deskripsi") String deskripsi,
        @RequestParam("hargaProduk") int hargaProduk,
        @RequestParam("stokProduk") int stokProduk,
        @RequestParam(value = "image", required = false) MultipartFile file
    ) {
        try {
            ProdukMakanan produkMakanan = produkMakananService.addProdukMakanan(
                namaProduk, jenisMakanan, hargaProduk, stokProduk, file
            );
            return ResponseEntity.ok(produkMakanan);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // membaca semua produk makanan
    @GetMapping
    public List<ProdukMakanan> getProdukMakanan() {
        return produkMakananService.getAllProdukMakanan();
    }

    // memperbarui produk makanan dengan id
    // @PutMapping("/{id}")
    // public ProdukMakanan putProdukMakanan(@PathVariable String id, @RequestBody ProdukMakanan produkMakanan) {
    //     return produkMakananService.updateProdukMakanan(id, produkMakanan);
    // }

    @PutMapping("/{id}")
    public ResponseEntity<ProdukMakanan> updateProdukMakanan(
    @PathVariable String id,
    @RequestParam(value = "namaProduk", required = false) String namaProduk,
    @RequestParam(value = "jenisMakanan", required = false) String jenisMakanan,
    // @RequestParam(value = "deskripsi", required = false) String deskripsi,
    @RequestParam(value = "hargaProduk", required = false) Integer hargaProduk,
    @RequestParam(value = "stokProduk", required = false) Integer stokProduk,
    @RequestParam(value = "image", required = false) MultipartFile file
) {
    try {
        ProdukMakanan updatedProduk = produkMakananService.updateProdukMakanan(
            id, namaProduk, jenisMakanan, hargaProduk, stokProduk, file
        );
        return ResponseEntity.ok(updatedProduk);
    } catch (NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

    @DeleteMapping("/{id}")
    public void deleteProdukMakanan(@PathVariable String id){
        produkMakananService.deleteProdukMakanan(id);
    }
    
}
