package com.cemilanku.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cemilanku.model.Roles;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Ambil token dari cookie
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        if (token == null) {
            logger.info("JWT tidak ditemukan di cookie, melanjutkan tanpa autentikasi.");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Validasi token
            String email = jwtUtil.extractEmail(token);
            Roles role = jwtUtil.extractRole(token);

            if (email != null && role != null) {
                logger.info("Autentikasi dengan token: " + token);
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.name());
                User userDetails = new User(email, "", Collections.singletonList(authority));
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                logger.warn("Token tidak valid atau user tidak ditemukan.");
                clearJwtCookie(response);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token tidak valid, silakan login kembali");
                return;
            }
        } catch (ExpiredJwtException e) {
            logger.warn("Token JWT telah kedaluwarsa.");
            clearJwtCookie(response);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired, silakan login kembali");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void clearJwtCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);  // Hapus cookie
        response.addCookie(cookie);
    }
}