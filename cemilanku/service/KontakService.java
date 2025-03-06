package com.cemilanku.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cemilanku.model.Kontak;
import com.cemilanku.repository.KontakRepository;

@Service
public class KontakService {
    @Autowired
    private KontakRepository kontakRepository;

    // membuat kontak
    public Kontak createKontak(Kontak kontak){
        return kontakRepository.save(kontak);
    }

    // membaca kontak
    public List<Kontak> getAllKontak(){
        return kontakRepository.findAll();
    }

    // memperbarui kontak dengan id
    public Kontak updateKontak(String id, Kontak kontak){
    Kontak updateKontak = kontakRepository.findById(id).orElseThrow(() -> new RuntimeException("kontak tidak diketemui."));
    if(kontak.getPesan() != null){
        updateKontak.setPesan(kontak.getPesan());
    }
    if(kontak.getEmail() != null){
        updateKontak.setEmail(kontak.getEmail());
    }
    return kontakRepository.save(updateKontak); 
}

    // menghapuskan kontak dengan id
    public void deleteKontak(String id){
        if(kontakRepository.existsById(id)){
            kontakRepository.deleteById(id);
        }
        else{
            ResponseEntity.notFound();
        }
    }
}
