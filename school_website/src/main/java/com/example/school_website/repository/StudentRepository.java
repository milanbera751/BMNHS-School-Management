package com.example.school_website.repository;

import com.example.school_website.model.Student;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByUsername(String username);
//    boolean existsByUsername(String username);
    List<Student> findByCourse(String course);
    Optional<Student> findByCourseAndRoll(String course, String roll);
}
