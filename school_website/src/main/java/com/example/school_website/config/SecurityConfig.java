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
                .cors(cors->{})
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.cacheControl(cache->cache.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/students/attendance").permitAll()
                        .requestMatchers("/","/index.html","/favicon.ico",
                                "/css/**","/js/**","/image/**","/Dashboard/**").permitAll()
                        .requestMatchers("/login.html/**","/api/teacher/staff").permitAll()
                        .requestMatchers("/pages/**").permitAll()
                        .requestMatchers("/api/notices/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/gallery/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/gallery/**").authenticated()
                        .requestMatchers("/notice.html").hasRole("ADMIN")
                        .requestMatchers("/StudentRegister.html","/TeacherRegister.html").hasRole("ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/students/**").hasAnyRole("STUDENT","TEACHER")
                        .requestMatchers("/api/teacher/**").hasRole("TEACHER")
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}