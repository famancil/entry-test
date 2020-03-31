package com.java.prueba.entry.controllers;

import com.java.prueba.entry.entities.Course;
import com.java.prueba.entry.entities.ICourse;
import com.java.prueba.entry.entities.IStudent;
import com.java.prueba.entry.entities.Student;
import com.java.prueba.entry.services.CourseService;
import com.java.prueba.entry.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controller for management students
 */
@RestController
@RequestMapping("/students")
public class StudentController implements IStudentController{

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Override
    public Page<IStudent> findAllIPage(Pageable pageable) { return null;}

    /**
     * Return the first 15 students as a Page.
     * @param pageable
     * @return Response Entity of Page of student.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Student>> findAllPage(Pageable pageable) {

        try {
            Page<Student> students = studentService.findAllStudent(pageable);
            if (students.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(students, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<IStudent> findIAll() { return null;}

    /**
     * Return a List of Student.
     * @return Response Entity of List Of Student.
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<Student>> findAll(Pageable pageable) {

        try {
            List<Student> students = studentService.findAll();
            if (students.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(students, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public IStudent findIById(Long id) { return null;}

    /**
     * Return a student with a specific id.
     * @param id
     * @return Response Entity of Student
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Student> findById(@PathVariable(value = "id") Long id) {

        try {
            Student studentResult = studentService.findById(id);
            if (studentResult == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(studentResult, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public IStudent Isave(IStudent course) {
        return null;
    }

    /**
     * Return a course saved in database
     * @param student
     * @return Response Entity of Student
     */
    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Student> save(@RequestBody HashMap<String,String> student) {
        try {

            //If rut is not valid.
            if(!isRutValid(student.get("rut")))
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            Student _student = new Student(formatRut(student.get("rut")),student.get("name"),
                    student.get("lastName"),Integer.parseInt(student.get("age")));

            Course course;

            //If value of key "course_id" is null or not.
            if(student.get("course_id")!=null)
                course = courseService.findById(Long.parseLong(student.get("course_id")));
            else
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            _student.setCourse(course);

            return new ResponseEntity<>(studentService.save(_student), HttpStatus.CREATED);
        }catch (NoSuchElementException | RollbackException | ConstraintViolationException | TransactionSystemException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Return a course updated in database
     * @param student
     * @return Response Entity of Student
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<Student> update(@PathVariable(value = "id") Long id, @RequestBody HashMap<String,String> student) {
        try {
            Student _student = studentService.findById(id);

            //If student not found
            if (_student == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            //If rut exist and is not valid
            else if (student.get("rut") != null && !isRutValid(student.get("rut")))
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            else {

                //If exist any of value of keys.
                if (student.get("name") != null) _student.setName(student.get("name"));
                if (student.get("lastName") != null) _student.setName(student.get("lastName"));
                if (student.get("age") != null) _student.setName(student.get("age"));
                if (student.get("rut") != null) _student.setName(student.get("rut"));

                Course course;

                //If value of key "course_id" is null
                if (student.get("course_id") != null) {
                    course = courseService.findById(Long.parseLong(student.get("course_id")));
                    _student.setCourse_id(course.getId());
                    _student.setCourse(course);
                }

                return new ResponseEntity<>(studentService.save(_student), HttpStatus.OK);
            }
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch (RollbackException | ConstraintViolationException | TransactionSystemException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Remove a course with specific id.
     * @param id
     * @return Response Entity of Course
     */
    @Override
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteById(@PathVariable(value = "id") Long id) {
        try {
            studentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Function to validate rut
     */
    public boolean isRutValid(String rut) {

        boolean validate = false;
        try {
            rut = rut.toUpperCase();

            //Convert to string without hiphen and points.
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");

            //Rut without check number
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            //Check number
            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;

            //Calculate if rut is valid
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validate = true;
            }
            return validate;

        }catch (Exception e) {
            return validate;
        }
    }

    /**
     * Function to format rut (with hyphen and points)
     */
    public String formatRut(String rut) {
        int count = 0;
        String format;

        //Convert to string without hiphen and points.
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");

        if(rut.length()<2) return rut;

        //Start from end (check digit)
        format = "-" + rut.substring(rut.length() - 1);

        //From end of rut without check digit to first number
        for (int i = rut.length() - 2; i >= 0; i--) {
            format = rut.substring(i, i + 1) + format;
            count++;
            if (count == 3 && i != 0) {
                format = "." + format;
                count = 0;
            }
        }

        return format;
    }
}
