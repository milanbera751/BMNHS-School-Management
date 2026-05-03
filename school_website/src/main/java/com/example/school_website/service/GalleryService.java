package com.example.school_website.service;

import com.example.school_website.model.Gallery;
import com.example.school_website.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    public Gallery save(Gallery g) {
        return galleryRepository.save(g);
    }

    public List<Gallery> getAll() {
        return galleryRepository.findAll();
    }
}