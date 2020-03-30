package com.java.prueba.entry.controllers;

import com.java.prueba.entry.entities.ICourse;
import com.java.prueba.entry.entities.IStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IStudentController {

    public Page<IStudent> findAllIPage(Pageable pageable);
    public List<IStudent> findIAll();
    public IStudent findIById(Long id);
    public IStudent Isave(IStudent course);
    public ResponseEntity<HttpStatus> deleteById(Long id);

}
