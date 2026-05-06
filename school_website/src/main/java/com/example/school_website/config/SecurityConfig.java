package com.example.school_website.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.cacheControl(cache->cache.disable()))
                .authorizeHttpRequests(auth -> auth
                        // 1. Core Error and Google Verification Routing (Permit All)
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/google*.html").permitAll() // Matches any Google verification HTML file

                        // 2. Static Resources & Dynamic Routes (Thymeleaf Home / Login Views)
                        .requestMatchers("/", "/index", "/login", "/favicon.ico").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/image/**", "/Dashboard/**").permitAll()

                        // 3. API & Public Endpoints
                        .requestMatchers("/api/students/attendance").permitAll()
                        .requestMatchers("/login.html/**", "/api/teacher/staff").permitAll()
                        .requestMatchers("/pages/**").permitAll()
                        .requestMatchers("/api/notices/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/gallery/**").permitAll()

                        // 4. Role-based Authentication Rules
                        .requestMatchers(HttpMethod.POST, "/api/gallery/**").authenticated()
                        .requestMatchers("/notice.html").hasRole("ADMIN")
                        .requestMatchers("/StudentRegister.html", "/TeacherRegister.html").hasRole("ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/students/**").hasAnyRole("STUDENT", "TEACHER")
                        .requestMatchers("/api/teacher/**").hasRole("TEACHER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*")); // For testing; use specific URL later
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}