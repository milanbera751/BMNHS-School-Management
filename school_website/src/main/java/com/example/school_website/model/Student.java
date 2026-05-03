package com.example.school_website.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;   // use email or unique username

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String course;     // optional (class/course)

    @Column(nullable = false)
    private String phone;

    @Column(nullable =false, unique = true)
    private String roll;

    @Column(nullable = false)
    private String role ="STUDENT";

    @Column(nullable = false)
    private boolean firstLogin = true;

    public Student() {
    }
    public Student(String name, String username, String password, String course,String roll, String section,String phone) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.course = course;
        this.roll = roll;
        this.phone=phone;
    }

}