package com.example.school_website.dto;

import com.example.school_website.model.Attendance;
import lombok.Data;

import java.util.List;

@Data
public class BulkAttendanceRequest {
    private String subject;
    private String date;
    private List<AttendanceItem> list;

    @Data
    public static class AttendanceItem {
        private String studentUsername;
        private String status;
    }
}
