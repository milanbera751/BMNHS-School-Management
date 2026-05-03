package com.example.school_website.service;

import com.example.school_website.repository.StudentRepository;
import com.example.school_website.repository.TeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //DASHBOARD STATS
    public Map<String, Long> getDashboardStats() {

        long totalStudents = studentRepository.count();
        long totalTeachers = teacherRepository.count();

        Map<String, Long> stats = new HashMap<>();
        stats.put("students", totalStudents);
        stats.put("teachers", totalTeachers);

        return stats;
    }
}