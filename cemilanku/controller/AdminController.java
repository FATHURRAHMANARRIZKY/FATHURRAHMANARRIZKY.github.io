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

import com.cemilanku.model.Admin;
import com.cemilanku.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    // membuat admin
    @PostMapping
    public Admin createAdmin(@Valid @RequestBody Admin admin){
        return adminService.createAdmin(admin);
    }
    // membaca semua admin
    @GetMapping
    public List<Admin> getAllAdmin(){
        return adminService.getAllAdmin();
    }
    // memperbarui admin dengan id
    @PutMapping("/{id}")
    public Admin updateAdmin(@PathVariable String id, @RequestBody Admin admin){
        return adminService.updateAdmin(id, admin);
    }
    // menghapus admin dengan id
    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable String id){
        adminService.deleteAdminById(id);
    }
}
