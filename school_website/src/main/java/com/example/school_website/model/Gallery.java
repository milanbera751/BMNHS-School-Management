package com.example.school_website.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
}