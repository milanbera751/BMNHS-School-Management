package com.example.school_website.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NoticeResponse {
    private String title;
    private String content;
    private LocalDate date;

    public NoticeResponse(String title, String content, LocalDate date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
