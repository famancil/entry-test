package com.java.prueba.entry.services;

import com.java.prueba.entry.entities.Course;
import com.java.prueba.entry.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service that works the management of courses.
 */
@Service
public class CourseService implements ICourseService {

    @Autowired
    CourseRepository courseRepository;

    /**
     * Return the first 15 courses as a Page.
     * @param pageable
     * @return Page of Course.
     */
    @Override
    public Page<Course> findAllPage(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    /**
     * Return a List of Course.
     * @return List Of Course.
     */
    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    /**
     * Return a course with a specific id.
     * @param id
     * @return Course
     */
    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).get();
    }

    /**
     * Return a course saved in database
     * @param course
     * @return Course
     */
    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Remove a course with specific id.
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }
}
