package com.java.prueba.entry.services;

import com.java.prueba.entry.entities.Course;
import com.java.prueba.entry.entities.Student;
import com.java.prueba.entry.repositories.CourseRepository;
import com.java.prueba.entry.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service that works the management of students.
 */
@Service
public class StudentService implements IStudentService {

    @Autowired
    StudentRepository studentRepository;

    /**
     * Return the first 15 students as a Page.
     * @param pageable
     * @return Page of student.
     */
    @Override
    public Page<Student> findAllStudent(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    /**
     * Return a List of Student.
     * @return List Of Student.
     */
    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    /**
     * Return a student with a specific id.
     * @param id
     * @return Student
     */
    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).get();
    }

    /**
     * Return a course saved in database
     * @param student
     * @return Student
     */
    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Remove a course with specific id.
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
