package com.example.school_website.controller;

import com.example.school_website.model.Marks;
import com.example.school_website.model.Student;
import com.example.school_website.repository.MarksRepository;
import com.example.school_website.repository.NoticeRepository;
import com.example.school_website.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import com.example.school_website.service.AttendanceService;
import com.example.school_website.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MarksRepository marksRepository;

    //view profile
    @GetMapping("/profile")
    public String profile(){
        return("wellcome to student Profile");
    }

    //
    @GetMapping
    @PreAuthorize("hasRole('TEACHER')")
    public List<Student>getStudents(@RequestParam("class")String className){
        return studentRepository.findByCourse(className);
    }

    //view attendance
    @GetMapping("/attendance")
    @PreAuthorize("hasRole('STUDENT')")
    public List<Map<String, Object>> getMyAttendance(Authentication auth) {

        String username = auth.getName();

        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Student not found for username: " + username));

        return attendanceService.getStudentAttendanceSummary(student.getId());
    }
    @GetMapping("/my-marks")
    public ResponseEntity<List<Marks>> getMyMarks(Principal principal) {
        // Principal contains the username of the logged-in student
        String username = principal.getName();

        // Find student by username first
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Student profile missing"));

        // Find all marks linked to this student
        List<Marks> myMarks = marksRepository.findByStudent(student);
        return ResponseEntity.ok(myMarks);
    }

}
