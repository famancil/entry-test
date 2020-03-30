package com.java.prueba.entry.services;

import com.java.prueba.entry.entities.Course;
import com.java.prueba.entry.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStudentService {

    public Page<Student> findAllStudent(Pageable pageable);
    public List<Student> findAll();
    public Student findById(Long id);
    public Student save(Student student);
    public void deleteById(Long id);
}
