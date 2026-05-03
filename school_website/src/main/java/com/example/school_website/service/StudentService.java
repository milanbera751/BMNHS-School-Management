package com.example.school_website.service;

import com.example.school_website.model.Student;
import com.example.school_website.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Register Student
    public Student register(Student s) {
        if (repo.findByUsername(s.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        // Encode password
//        s.setPassword(passwordEncoder.encode(s.getPassword().trim()));
        String rawPassword = s.getPassword().trim();
        s.setPassword(passwordEncoder.encode(rawPassword));
        s.setRole("STUDENT");

        return repo.save(s);
    }
    public List<Student> getAll() {
        return repo.findAll();
    }
    public Optional<Student> findByUsername(String username) {
        return repo.findByUsername(username);
    }
    public long count() {
        return repo.count();
    }
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
