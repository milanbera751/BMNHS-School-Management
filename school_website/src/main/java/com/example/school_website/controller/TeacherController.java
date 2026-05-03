package com.example.school_website.controller;

import com.example.school_website.dto.BulkAttendanceRequest;
import com.example.school_website.dto.TeacherDto;
import com.example.school_website.model.Marks;
import com.example.school_website.model.Teacher;
import com.example.school_website.repository.NoticeRepository;
import com.example.school_website.repository.TeacherRepository;
import com.example.school_website.service.AttendanceService;
import com.example.school_website.service.MarksService;
import com.example.school_website.service.NoticeService;
import com.example.school_website.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherRepository TeacherRepository;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private TeacherService TeacherService;
    @Autowired
    private MarksService marksService;

    //add attendance
    @PostMapping("/attendance/bulk")
    @PreAuthorize("hasRole('TEACHER')")
    public String bulkAttendance(@RequestBody BulkAttendanceRequest req,
                                 Authentication auth) {

        String username = auth.getName();

        Teacher teacher = TeacherRepository.findByUsername(username)
                .orElseThrow(()-> new NoSuchElementException("Teacher Not Found with username: " + username));

        attendanceService.saveBulk(req, teacher);

        return "Saved";
    }

   //staff field
    @GetMapping("/staff")
    public List<TeacherDto> getAllTeachers() {
        return TeacherService.getAllTeachers()
                .stream()
                .map(TeacherDto::new)
                .toList();
    }
    @PostMapping("/add-marks/{course}/{roll}")
    public ResponseEntity<?> addMarks(@PathVariable String course,
                                      @PathVariable String roll,
                                      @RequestBody Marks marks) {
        try {
            Marks saved = marksService.addMarks(course, roll, marks);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
