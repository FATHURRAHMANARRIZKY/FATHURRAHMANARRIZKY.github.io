package com.cemilanku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.CrossOrigin;

import com.cemilanku.model.LoginRequest;
import com.cemilanku.model.User;
import com.cemilanku.security.JwtUtil;
import com.cemilanku.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import com.cemilanku.model.Admin;

// @CrossOrigin(origins = "http://10.20.20.213:5173", allowCredentials = "true")
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
    System.out.println("📥 Request diterima:");
    System.out.println("   📧 Email: " + loginRequest.getEmail());
    System.out.println("   🔑 Password: " + loginRequest.getKataSandi());

    // Coba autentikasi sebagai Admin
    Admin admin = loginService.authenticate(loginRequest.getEmail(), loginRequest.getKataSandi());

    if (admin != null) {
        return loginService.generateLoginResponse(admin, response);
    }

    // Coba autentikasi sebagai User
    User user = loginService.authenticateUser(loginRequest.getEmail(), loginRequest.getKataSandi());

    if (user != null) {
        return loginService.generateLoginResponse(user, response);
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email atau kata sandi salah");
    }

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@CookieValue(name = "jwt_token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token tidak ditemukan, silakan login.");
        }

        try {
            String email = jwtUtil.extractEmail(token);
            if (jwtUtil.isTokenValid(token, email)) {
                return ResponseEntity.ok().body("Token valid");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token tidak valid.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token tidak valid atau sudah kadaluarsa.");
        }
    }

    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return loginService.logout(request, response);
    }

    @GetMapping("/isLoggedIn")
    public ResponseEntity<Boolean> isLoggedIn(@CookieValue(name = "jwt_token", required = false) String token) {
        return ResponseEntity.ok(token != null);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, HttpServletResponse response){
        return loginService.registerUser(user, response);
    }
}
