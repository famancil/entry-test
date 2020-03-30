package com.java.prueba.entry.controllers;


import com.java.prueba.entry.entities.ICourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICourseController {

    public Page<ICourse> findAllIPage(Pageable pageable);
    public List<ICourse> findIAll();
    public ICourse findIById(Long id);
    public ICourse Isave(ICourse course);
    public ResponseEntity<HttpStatus> deleteById(Long id);


}
