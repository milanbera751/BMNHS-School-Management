package com.example.school_website.service;


import com.example.school_website.model.Marks;
import com.example.school_website.model.Student;
import com.example.school_website.repository.MarksRepository;
import com.example.school_website.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;
    @Autowired private StudentRepository studentRepository;

    public Marks addMarks(String course, String roll, Marks marks) {
        // 1. Find student by class and roll
        Student student = studentRepository.findByCourseAndRoll(course, roll)
                .orElseThrow(() -> new RuntimeException("Student not found in Class " + course + " with Roll " + roll));

        // 2. Attach student to marks
        marks.setStudent(student);

        // 3. Percentage calculation (Standard 100 base)
        if (marks.getTotalMarks() != null && marks.getTotalMarks() > 0) {
            marks.setPercentage((marks.getMarksObtained() / marks.getTotalMarks()) * 100);
        }

        return marksRepository.save(marks);
    }
}