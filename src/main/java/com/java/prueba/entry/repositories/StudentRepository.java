package com.java.prueba.entry.repositories;

import com.java.prueba.entry.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository that makes nexus between the entity Student and his table in DB.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
