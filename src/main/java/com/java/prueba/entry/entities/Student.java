package com.java.prueba.entry.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "student")
public class Student implements IStudent {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column
    private String rut;
    @Column
    private String name;
    @Column
    private String lastName;
    @Column
    @NotNull(message = "Age must not be null")
    //@Range(min=1, max=10000,message = "Age must be 18 at least")
    @Min(value = 19, message = "Age must be 19 at least")
    private int age;

    /*@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Course> courses;*/

    @ManyToOne
    @JoinColumn
    @NotNull(message = "Student must have a course")
    private Course course;

    public Student(String rut,String name,String lastName,int age){
        this.rut = rut;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Student(){}

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
}
