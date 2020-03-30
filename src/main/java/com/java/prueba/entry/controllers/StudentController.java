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

@RestController
@RequestMapping("/students")
public class StudentController implements IStudentController{

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Override
    public Page<IStudent> findAllIPage(Pageable pageable) { return null;}

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

    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Student> save(@RequestBody HashMap<String,String> student) {
        try {
            Student _student = new Student(student.get("rut"),student.get("name"),
                    student.get("lastName"),Integer.parseInt(student.get("age")));

            Course course;

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

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<Student> update(@PathVariable(value = "id") Long id, @RequestBody HashMap<String,String> student) {
        try {
            Student _student = studentService.findById(id);

            if(_student == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else {
                _student.setName(student.get("name"));
                _student.setLastName(student.get("lastName"));
                _student.setAge(Integer.parseInt(student.get("age")));
                _student.setRut(student.get("rut"));

                Course course;

                if(student.get("course_id")!=null)
                    course = courseService.findById(Long.parseLong(student.get("course_id")));
                else
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

                _student.setCourse(course);

                return new ResponseEntity<>(studentService.save(_student), HttpStatus.OK);
            }
        }catch (NoSuchElementException | RollbackException | ConstraintViolationException | TransactionSystemException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
}
