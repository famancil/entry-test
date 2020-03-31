package com.java.prueba.entry.controllers;

import com.java.prueba.entry.entities.Course;
import com.java.prueba.entry.entities.ICourse;
import com.java.prueba.entry.services.CourseService;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Controller for management courses.
 */
@RestController
@RequestMapping("/courses")
public class CourseController implements ICourseController {

    @Autowired
    private CourseService courseService;

    @Override
    public Page<ICourse> findAllIPage(Pageable pageable) { return null;}

    /**
     * Return the first 15 courses as a Page.
     * @param pageable
     * @return Page of Course.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Course>> findAllPage(Pageable pageable) {

        try {
            Page<Course> courses = courseService.findAllPage(pageable);
            if (courses.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(courses, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ICourse> findIAll() { return null; }

    /**
     * Return a List of Course.
     * @return Response Entity of List Of Course.
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<Course>> findAll(Pageable pageable) {

        try {
            List<Course> courses = courseService.findAll();
            if (courses.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ICourse findIById(Long id) { return null; }

    /**
     * Return a course with a specific id.
     * @param id
     * @return Response Entity of Course
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Course> findById(@PathVariable(value = "id") Long id) {

        try {
            Course courseResult = courseService.findById(id);
            if (courseResult == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(courseResult, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ICourse Isave(ICourse course) { return null;}

    /**
     * Return a course saved in database
     * @param course
     * @return Response Entity of Course
     */
    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Course> save(@RequestBody Course course) {

        try {
            Course _course = courseService.save(course);
            return new ResponseEntity<>(_course, HttpStatus.CREATED);
        }catch (NoSuchElementException | RollbackException | ConstraintViolationException | TransactionSystemException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Return a course updated in database
     * @param id
     * @param course
     * @return Response Entity of Course
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<Course> update(@PathVariable(value = "id") Long id, @RequestBody Course course) {
        try {
            Course _course = courseService.findById(id);

            if (_course == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else {
                _course.setName(course.getName());
                _course.setCode(course.getCode());
                return new ResponseEntity<>(courseService.save(_course), HttpStatus.OK);
            }
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (RollbackException | ConstraintViolationException | TransactionSystemException e){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Remove a course with specific id.
     * @param id
     * @return Response Entity of HttpStatus
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @Override
    public ResponseEntity<HttpStatus> deleteById(@PathVariable(value = "id") Long id) {

        try {
            courseService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
