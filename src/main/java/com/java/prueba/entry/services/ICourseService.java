package com.java.prueba.entry.services;

import com.java.prueba.entry.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface for implements service(s) of course.
 */
public interface ICourseService {

    public Page<Course> findAllPage(Pageable pageable);
    public List<Course> findAll();
    public Course findById(Long id);
    public Course save(Course course);
    public void deleteById(Long id);
}
