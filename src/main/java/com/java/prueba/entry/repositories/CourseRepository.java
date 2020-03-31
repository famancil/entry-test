package com.java.prueba.entry.repositories;

import com.java.prueba.entry.entities.Course;
import com.java.prueba.entry.entities.ICourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository that makes nexus between the entity Course and his table in DB.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
}
