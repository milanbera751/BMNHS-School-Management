package com.example.school_website.dto;

import com.example.school_website.model.Teacher;
import lombok.Getter;

@Getter
public class TeacherDto {
    private String name;
    private String subject;
    private String phone;
    private String email;
    private String imageUrl;

    public TeacherDto(Teacher t) {
        this.name = t.getName();
        this.subject = t.getSubject();
        this.phone = t.getPhone();
        this.email = t.getEmail();
        this.imageUrl = t.getImageUrl();
    }

}
