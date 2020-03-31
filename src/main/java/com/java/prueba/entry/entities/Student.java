package com.java.prueba.entry.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Entity that maps the student's table
 */
@Entity(name = "student")
public class Student implements IStudent {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column
    @NotNull(message = "Rut must not be null")
    private String rut;
    @Column
    private String name;
    @Column
    private String lastName;

    //Age must be 19 at least
    @Column
    @NotNull(message = "Age must not be null")
    @Min(value = 19, message = "Age must be 19 at least")
    private int age;

    @Column(insertable = false, updatable = false)
    private long course_id;

    @ManyToOne
    @JoinColumn
    @NotNull(message = "Student must have a course")
    private Course course;

    //Constructor
    public Student(String rut,String name,String lastName,int age){
        this.rut = rut;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Student(){}

    //Getters and Setters
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getRut() {
        return rut;
    }

    @Override
    public void setRut(String rut) {
        this.rut = rut;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    @JsonIgnore
    public ICourse getICourse() {
        return course;
    }

    @Override
    @JsonIgnore
    public void setICourse(ICourse course) {}

    @JsonIgnore
    public Course getCourse() {
        return course;
    }

    @JsonIgnore
    public void setCourse(Course course) {
        this.course = course;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }
}
