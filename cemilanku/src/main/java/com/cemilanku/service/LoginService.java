package com.cemilanku.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cemilanku.model.Admin;
import com.cemilanku.model.LoginRequest;
import com.cemilanku.model.Roles;
import com.cemilanku.model.User;
import com.cemilanku.repository.AdminRepository;
import com.cemilanku.repository.UserRepository;
import com.cemilanku.security.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginService(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin authenticate(String email, String kataSandi) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        
        // Jika admin ditemukan, ambil datanya, jika tidak, return null
        Admin admin = optionalAdmin.orElse(null);

        // Cek apakah admin ditemukan dan password cocok
        if (admin != null && passwordEncoder.matches(kataSandi, admin.getKataSandi())) {
            return admin;
        }
        return null; // Login gagal
    }

    public ResponseEntity<?> login(LoginRequest loginRequest, HttpServletResponse response) {
        String email = loginRequest.getEmail();
        String kataSandi = loginRequest.getKataSandi();
    
        if (email == null || kataSandi == null) {
            return ResponseEntity.badRequest().body("Email atau Password harus diisi!");
        }
    
        Optional<User> userData = userRepository.findByEmail(email);
        Optional<Admin> adminData = adminRepository.findByEmail(email);
    
        System.out.println("🔍 Mencari email di database: " + email);
    
        if (userData.isPresent()) {
            User user = userData.get();
            System.out.println("✅ User ditemukan: " + user.getEmail());
            System.out.println("🔑 Password hash di database: " + user.getKataSandi());
    
            if (passwordEncoder.matches(kataSandi, user.getKataSandi())) {
                return generateLoginResponse(user, response); // 🔹 Memanggil generateLoginResponse!
            } else {
                System.out.println("❌ Password tidak cocok.");
                return ResponseEntity.status(401).body("Password salah.");
            }
        } else if (adminData.isPresent()) {
            Admin admin = adminData.get();
            System.out.println("✅ Ditemukan di tabel Admin: " + admin.getEmail());
    
            if (passwordEncoder.matches(kataSandi, admin.getKataSandi())) {
                return generateLoginResponse(admin, response); // 🔹 Memanggil generateLoginResponse!
            } else {
                return ResponseEntity.status(401).body("Password salah.");
            }
        } else {
            System.out.println("❌ Email tidak ditemukan di database.");
            return ResponseEntity.status(401).body("Email tidak ditemukan.");
        }
    }
    
    
    

    public ResponseEntity<?> registerUser(User user, HttpServletResponse response) {
        if (user.getEmail() == null || user.getKataSandi() == null) {
            return ResponseEntity.badRequest().body("Email dan kata sandi harus diisi.");
        }
    
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body("Email sudah digunakan.");
        }
    
        if (user.getRole() == null) {
            user.setRole(Roles.USER);
        }
    
        user.setKataSandi(passwordEncoder.encode(user.getKataSandi()));
    
        userRepository.save(user);
    
        String token;
        if (user.getRole() == Roles.ADMIN) {
            token = jwtUtil.generateToken(user);
        } else {
            token = jwtUtil.generateToken(user);
        }
    
        Cookie cookie = new Cookie("jwt_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Ubah menjadi true jika HTTPS digunakan
        cookie.setPath("/");
        cookie.setDomain("localhost"); // Atau sesuaikan dengan domain frontend
        cookie.setMaxAge(60 * 60 * 24); 

        response.addCookie(cookie);

    
        response.addCookie(cookie);
    
        return ResponseEntity.ok("Registrasi sukses! JWT token telah disimpan di cookie.");
    }    

    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
    
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Hapus cookie
        response.addCookie(cookie);
    
        return ResponseEntity.ok("Logout sukses!");
    }    

    public User authenticateUser(String email, String kataSandi) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    User user = optionalUser.orElse(null);

    if (user != null && passwordEncoder.matches(kataSandi, user.getKataSandi())) {
        return user;
    }
    return null;
}

public ResponseEntity<?> generateLoginResponse(Object user, HttpServletResponse response) {
    String token;
    if (user instanceof Admin) {
        token = jwtUtil.generateToken((Admin) user);
    } else {
        token = jwtUtil.generateToken((User) user);
    }

    Cookie cookie = new Cookie("jwt_token", token);
    cookie.setHttpOnly(true);
    cookie.setSecure(false); // Set `true` jika pakai HTTPS
    cookie.setPath("/");
    cookie.setDomain("10.20.20.213"); // Sesuaikan dengan domain backend
    cookie.setMaxAge(24 * 60 * 60); // 1 hari
    response.addCookie(cookie);

    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("token", token);
    responseBody.put("role", (user instanceof Admin) ? ((Admin) user).getRole().toString() : ((User) user).getRole().toString());

    return ResponseEntity.ok(responseBody);
}

}
