package com.example.school_website.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role="TEACHER"; // TEACHER or ADMIN

    private String imageUrl;
    // Constructors
    public Teacher() {}

    public Teacher(String name, String subject,String username, String phone,String email,String password,String imageUrl) {
        this.name = name;
        this.subject = subject;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
    }
}