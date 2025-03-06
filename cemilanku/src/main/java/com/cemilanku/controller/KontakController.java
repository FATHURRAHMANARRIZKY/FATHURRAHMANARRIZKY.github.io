package com.cemilanku.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cemilanku.model.Kontak;
import com.cemilanku.service.KontakService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/kontak")
public class KontakController {
    @Autowired
    private KontakService KontakService;

    // membuat kontak
    @PostMapping
    public Kontak createKontak(@Valid @RequestBody Kontak kontak){
        return KontakService.createKontak(kontak);
    }

    // membaca kontak
    @GetMapping
    public List<Kontak> getAllKontak(){
        return KontakService.getAllKontak();
    }

    // memperbarui kontak dengan id
    @PutMapping("/{id}")
    public Kontak updateKontak(@Valid @PathVariable String id, @RequestBody Kontak kontak){
        return KontakService.updateKontak(id, kontak);
    }

    // menghapus kontak dengan id
    @DeleteMapping("/{id}")
    public void deleteKontak(@PathVariable String id){
        KontakService.deleteKontak(id);
    }
}
