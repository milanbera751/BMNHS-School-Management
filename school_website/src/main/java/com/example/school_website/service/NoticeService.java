package com.example.school_website.service;

import com.example.school_website.model.Notice;
import com.example.school_website.model.Teacher;
import com.example.school_website.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository repo;

    // Upload directory
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    public Notice createNoticeWithFile(String title,
                                       String content,
                                       MultipartFile file,
                                       Teacher admin) throws IOException {

        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setCreateAt(LocalDateTime.now());
        notice.setAdmin(admin);

        // Handle file upload
        if (file != null && !file.isEmpty()) {

            // Create folder if not exists
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Validate file name
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                throw new RuntimeException("Invalid file name");
            }

            // Optional: file size limit (5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                throw new RuntimeException("File size exceeds 5MB");
            }

            // Create unique file name
            String fileName = System.currentTimeMillis() + "_" + originalFilename;

            // Save file
            File destination = new File(UPLOAD_DIR + fileName);
            file.transferTo(destination);

            // Save relative path (better than absolute)
            notice.setFileName(fileName);
            notice.setFilePath("/uploads/" + fileName);
        }

        return repo.save(notice);
    }

    // Get all notices
    public List<Notice> getAllNotices() {
        return repo.findAll();
    }

    // Get notice by ID
    public Notice getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));
    }

    // Delete notice (optional but useful)
    public void deleteNotice(Long id) {
        Notice notice = getById(id);

        // Delete file if exists
        if (notice.getFilePath() != null) {
            File file = new File(System.getProperty("user.dir") + notice.getFilePath());
            if (file.exists()) {
                file.delete();
            }
        }

        repo.deleteById(id);
    }
}