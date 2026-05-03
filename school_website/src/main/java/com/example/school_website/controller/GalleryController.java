package com.example.school_website.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.school_website.model.Gallery;
import com.example.school_website.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gallery")
@CrossOrigin
public class GalleryController {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private GalleryService galleryService;

    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    @PostMapping("/add")
    public ResponseEntity<Gallery> addPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        // We let the IOException bubble up so the Global Handler can catch it
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));

        String imageUrl = uploadResult.get("secure_url").toString();
        Gallery gallery = new Gallery();
        gallery.setImageUrl(imageUrl);

        return ResponseEntity.ok(galleryService.save(gallery));
    }
    @GetMapping
    public List getGallery(Authentication auth) {
        return galleryService.getAll();
    }
}
