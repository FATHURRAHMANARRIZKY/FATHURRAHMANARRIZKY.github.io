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

import com.cemilanku.model.User;
import com.cemilanku.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

     // membuat user
    @PostMapping
    public User createUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }
    // membaca semua user
    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUser();
    }
    // memperbarui user dengan id
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user){
        return userService.updateUser(id, user);
    }
    // menghapus admin dengan id
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable String id){
        userService.deleteUserById(id);
    }
}
