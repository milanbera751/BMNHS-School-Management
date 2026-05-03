package com.example.school_website.controller;

import com.example.school_website.dto.LoginRequest;
import com.example.school_website.model.Student;
import com.example.school_website.model.Teacher;
import com.example.school_website.repository.StudentRepository;
import com.example.school_website.repository.TeacherRepository;

import com.example.school_website.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest req) {
    String username = (req.getUsername() != null) ? req.getUsername().trim() : "";
    String rawPassword = (req.getPassword() != null) ? req.getPassword().trim() : "";

    Map<String, String> response = new HashMap<>();

    // 1. Check Student Table
    Optional<Student> studentOpt = studentRepo.findByUsername(username);
    if (studentOpt.isPresent()) {
        if (passwordEncoder.matches(rawPassword, studentOpt.get().getPassword())) {
            String token = jwtService.generateToken(username, "STUDENT");
            return createResponse("STUDENT", token);
        }
        // If password doesn't match here, it might be a Teacher with same username
    }

    // 2. Check Teacher/Admin Table
    Optional<Teacher> teacherOpt = teacherRepo.findByUsername(username);
    if (teacherOpt.isPresent()) {
        if (passwordEncoder.matches(rawPassword, teacherOpt.get().getPassword())) {
            String role = teacherOpt.get().getRole();
            String token = jwtService.generateToken(username, role);
            return createResponse(role, token);
        }
    }

    // 3. Final Fallback
    // If we are here, either the user doesn't exist OR the password was wrong for both
    if (studentOpt.isPresent() || teacherOpt.isPresent()) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid password"));
        throw new IllegalArgumentException("Invalid Password provided for user"+username);
    }

//    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
    throw new IllegalArgumentException("User Not found with username"+username);
}

    // Helper method to keep code clean
    private ResponseEntity<Map<String, String>> createResponse(String role, String token) {
        Map<String, String> res = new HashMap<>();
        res.put("token", token);
        res.put("role", role);
        return ResponseEntity.ok(res);
    }
}