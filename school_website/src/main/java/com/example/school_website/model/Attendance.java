package com.example.school_website.model;

import com.example.school_website.model.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String status;
    private LocalDate date;

    private String subject;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Teacher teacher;
}
