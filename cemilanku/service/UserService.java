package com.cemilanku.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cemilanku.model.Roles;
import com.cemilanku.model.User;
import com.cemilanku.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

     // membuat user
    public User createUser(User user){
        user.setKataSandi(passwordEncoder.encode(user.getKataSandi()));
        user.setRole(Roles.USER);
        return userRepository.save(user);
    }

    // membaca semua user
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    // memperbarui user dengan id
    public User updateUser(String id, User user){
        User updateUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User tidak diketemui!"));
        if(user.getNama() != null){
            updateUser.setNama(user.getNama());
        }
        else if(user.getEmail() != null){
            updateUser.setEmail(user.getEmail());
        }
        else if(user.getKataSandi() != null){
            updateUser.setKataSandi(passwordEncoder.encode(user.getKataSandi()));
        }
        else if(user.getRole() != null){
            updateUser.setRole(user.getRole());
        }
        return userRepository.save(updateUser);
    }

    // menghapus admin dengan id
    public void deleteUserById(String id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        }else{
            System.out.println("User with ID: " + id + " not found.");
        }
    }
}
