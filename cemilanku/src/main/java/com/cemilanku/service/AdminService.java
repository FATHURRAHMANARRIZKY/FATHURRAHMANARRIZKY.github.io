package com.cemilanku.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cemilanku.model.Admin;
import com.cemilanku.model.Roles;
import com.cemilanku.repository.AdminRepository;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // membuat admin
    public Admin createAdmin(Admin admin){
        admin.setKataSandi(passwordEncoder.encode(admin.getKataSandi()));
        admin.setRole(Roles.ADMIN);
        return adminRepository.save(admin);
    }
    // membaca semua admin
    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }
    // memperbarui admin dengan id
    public Admin updateAdmin(String id, Admin admin){
        Admin updateAdmin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin tidak diketemui."));
        if(admin.getNama() != null){
            updateAdmin.setNama(admin.getNama());
        }
        else if(admin.getEmail() != null){
            updateAdmin.setEmail(admin.getEmail());
        }
        else if(admin.getKataSandi() != null){
            updateAdmin.setKataSandi(passwordEncoder.encode(admin.getKataSandi()));
        }
        else if(admin.getRole() != null){
            updateAdmin.setRole(admin.getRole());
        }
        return adminRepository.save(updateAdmin);
    }
    // menghapus admin dengan id
    public void deleteAdminById(String id){
        if(adminRepository.existsById(id)){
            adminRepository.deleteById(id);
        }else{
            System.out.println("Admin with ID: " + id + " not found.");
        }
    }
}
