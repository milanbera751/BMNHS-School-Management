package com.example.school_website.service;

import com.example.school_website.dto.BulkAttendanceRequest;
import com.example.school_website.model.Attendance;
import com.example.school_website.model.Student;
import com.example.school_website.model.Teacher;
import com.example.school_website.repository.AttendanceRepository;
import com.example.school_website.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<Map<String, Object>> getStudentAttendanceSummary(Long studentId) {

        List<Attendance> list = attendanceRepository.findByStudentId(studentId);

        Map<String, List<Attendance>> grouped =
                list.stream().collect(Collectors.groupingBy(Attendance::getSubject));

        List<Map<String, Object>> result = new ArrayList<>();

        for (String subject : grouped.keySet()) {

            List<Attendance> records = grouped.get(subject);

            long total = records.size();
            long present = records.stream()
                    .filter(a -> a.getStatus().equals("PRESENT"))
                    .count();

            double percent = (present * 100.0) / total;

            Map<String, Object> row = new HashMap<>();
            row.put("subject", subject);
            row.put("totalClass", total);
            row.put("attended", present);
            row.put("percentage", percent);

            result.add(row);
        }

        // ✅ average
        double avg = result.stream()
                .mapToDouble(r -> (double) r.get("percentage"))
                .average()
                .orElse(0);

        Map<String, Object> avgRow = new HashMap<>();
        avgRow.put("subject", "AVERAGE");
        avgRow.put("percentage", avg);

        result.add(avgRow);

        return result;
    }
    public void saveBulk(BulkAttendanceRequest req, Teacher teacher) {

        // ✅ ADD VALIDATION HERE
        if (req.getDate() == null || req.getDate().isEmpty()) {
            throw new RuntimeException("Date is required");
        }

        LocalDate date;
        try{
            date =LocalDate.parse(req.getDate());
        }catch(DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date format");
        }

        for (BulkAttendanceRequest.AttendanceItem item : req.getList()) {

            Student student = studentRepository
                    .findByUsername(item.getStudentUsername())
                    .orElseThrow(() -> new RuntimeException("Student not found: " + item.getStudentUsername()));

            Attendance a = new Attendance();
            a.setStudent(student);
            a.setTeacher(teacher);
            a.setSubject(req.getSubject());
            a.setStatus(item.getStatus());
            a.setDate(date);

            attendanceRepository.save(a);
        }
    }
}
