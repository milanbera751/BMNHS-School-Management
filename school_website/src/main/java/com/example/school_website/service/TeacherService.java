package com.example.school_website.service;

import com.cloudinary.Cloudinary;
import com.example.school_website.model.Teacher;
import com.example.school_website.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CloudinaryService cloudinaryService;
//
//    // Register Teacher
//    public Teacher register(Teacher t) {
//
//        if (repo.existsByUsername(t.getUsername())) {
//            throw new RuntimeException("Username already exists");
//        }
//
//        t.setPassword(passwordEncoder.encode(t.getPassword()));
//
//        return repo.save(t);
//    }

    public Teacher register(
            String name,
            String subject,
            String username,
            String phone,
            String email,
            String password,
            MultipartFile image
    ) {

        if (repo.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        String imageUrl = cloudinaryService.uploadImage(image);

        Teacher t = new Teacher();
        t.setName(name);
        t.setSubject(subject);
        t.setUsername(username);
        t.setPhone(phone);
        t.setEmail(email);
        t.setPassword(passwordEncoder.encode(password));
        t.setRole("TEACHER");
        t.setImageUrl(imageUrl); // now full cloud URL

        return repo.save(t);
    }

    // Get all teachers
    public List<Teacher> getAllTeachers() {
        return repo.findAll();
    }

    // Find by username (login)
    public Optional<Teacher> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    // Count
    public long count() {
        return repo.count();
    }
}
