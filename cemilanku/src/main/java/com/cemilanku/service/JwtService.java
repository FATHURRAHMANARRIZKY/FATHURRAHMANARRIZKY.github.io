package com.cemilanku.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtService {
    public void setJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt_token", token);
        cookie.setHttpOnly(true);  
        cookie.setSecure(false);    
        cookie.setPath("/");       
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setDomain("localhost");
        response.addCookie(cookie);
    }
}
