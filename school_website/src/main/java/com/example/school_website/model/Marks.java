package com.example.school_website.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Marks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subjectName;
    private Double marksObtained;

    private Double totalMarks;

    private Double percentage;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;
}
