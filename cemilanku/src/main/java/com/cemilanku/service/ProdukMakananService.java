package com.cemilanku.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cemilanku.model.JenisMakanan;
import com.cemilanku.model.ProdukMakanan;
import com.cemilanku.repository.ProdukMakananRepository;

@Service
public class ProdukMakananService {
    @Autowired
    private ProdukMakananRepository produkMakananRepository;

    public ProdukMakanan addProdukMakanan(
        String namaProduk,
        String jenisMakanan,
        int hargaProduk,
        int stokProduk,
        MultipartFile file
    ) throws IOException {
        ProdukMakanan produkMakanan = new ProdukMakanan();
        produkMakanan.setNamaProduk(namaProduk);
        produkMakanan.setJenisMakanan(JenisMakanan.valueOf(jenisMakanan.toUpperCase()));
        produkMakanan.setHargaProduk(hargaProduk);
        produkMakanan.setStokProduk(stokProduk);

        // Jika ada file gambar, simpan sebagai Base64
        if (file != null) {
            String imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
            produkMakanan.setImageUrl("data:image/jpeg;base64," + imageBase64);
        }
        // Simpan ke database
        return produkMakananRepository.save(produkMakanan);
    }
    
    // membaca semua produk makanan
    public List<ProdukMakanan> getAllProdukMakanan(){
        return produkMakananRepository.findAll();
    }
    
     // memperbarui produk makanan dengan id
    public ProdukMakanan updateProdukMakanan(
    String id,
    String namaProduk,
    String jenisMakanan,
    // String deskripsi,
    Integer hargaProduk,
    Integer stokProduk,
    MultipartFile file
) throws IOException {
    // Cari produk berdasarkan ID
    ProdukMakanan produkMakanan = produkMakananRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Produk dengan ID " + id + " tidak ditemukan"));

    // Perbarui hanya field yang tidak null
    if (namaProduk != null) {
        produkMakanan.setNamaProduk(namaProduk);
    }
    if (jenisMakanan != null) {
        produkMakanan.setJenisMakanan(JenisMakanan.valueOf(jenisMakanan.toUpperCase()));
    }
    if (hargaProduk != null) {
        produkMakanan.setHargaProduk(hargaProduk);
    }
    if (stokProduk != null) {
        produkMakanan.setStokProduk(stokProduk);
    }
    if (file != null && !file.isEmpty()) {
        String imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
        produkMakanan.setImageUrl("data:image/jpeg;base64," + imageBase64);
    }

    // Simpan perubahan ke database
    return produkMakananRepository.save(produkMakanan);
}

    // menghapus produk makanan dengan id
    public void deleteProdukMakanan(String id){ 
        if(produkMakananRepository.existsById(id)){
            produkMakananRepository.deleteById(id);
        }else{
            throw new RuntimeException("produk makanan tidak diketemui.");
        }
    }
}