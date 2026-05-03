package com.example.school_website.controller;

import com.example.school_website.dto.RegisterRequest;
import com.example.school_website.model.Notice;
import com.example.school_website.model.Student;
import com.example.school_website.model.Teacher;
import com.example.school_website.repository.TeacherRepository;
import com.example.school_website.service.AdminService;

import com.example.school_website.service.NoticeService;
import com.example.school_website.service.StudentService;
import com.example.school_website.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository TeacherRepository;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-student")
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
        studentService.register(student);
        return ResponseEntity.ok("Student registered successfully");
    }

    //REGISTER TEACHER / ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-teacher")
    public Teacher registerTeacher(
            @RequestParam String name,
            @RequestParam String subject,
            @RequestParam String username,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam MultipartFile image
    ) {
        return teacherService.register(
                name, subject, username, phone, email, password, image
        );
    }
    //create notice
    @PostMapping("/notice")
    public Notice createNotice(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(value="file",required = false) MultipartFile file,
            Authentication auth) throws Exception {

        String username = auth.getName();

        Teacher admin = TeacherRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Admin profile not found!"+username));

        return noticeService.createNoticeWithFile(title, content, file, admin);
    }
}
