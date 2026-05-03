package com.example.school_website.controller;

import com.example.school_website.model.Notice;
import com.example.school_website.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notices")
    public List<Notice> getAllNotices() {
        return noticeService.getAllNotices();
    }
}