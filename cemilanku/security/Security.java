package com.cemilanku.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class Security implements WebMvcConfigurer {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public Security(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @SuppressWarnings("null")
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Sesuaikan dengan URL frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // ✅ Tambah OPTIONS
                .allowCredentials(true)
                .allowedHeaders("*");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/logout", "/error", "/isLoggedIn", "/produk-makanan",
                                "/review", "/admin", "/user", "/contacts", "/contacts/**")
                        .permitAll() // ✅ Tambah /isLoggedIn agar tidak kena 403
                        .requestMatchers("/produk-makanan/**", "/kontak/**", "/review/**", "/user/**")
                        .hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/admin/**", "/contacts", "/contacts/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            SecurityContextHolder.clearContext();

                            // Hapus cookie JWT
                            Cookie cookie = new Cookie("jwt_token", null);
                            cookie.setHttpOnly(true);
                            cookie.setSecure(false); // Ganti true jika pakai HTTPS
                            cookie.setPath("/");
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);

                            // ✅ Tambahkan CORS Headers agar logout tidak diblokir oleh CORS
                            response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
                            response.setHeader("Access-Control-Allow-Credentials", "true");

                            response.setStatus(HttpServletResponse.SC_OK);
                        })

                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
