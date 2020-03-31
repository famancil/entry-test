package com.java.prueba.entry.entities;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Entity that maps the course's table
 */
@Entity(name = "course")
public class Course implements ICourse{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column
    private String name;

    //Code must have length 6 chars
    @NotNull(message = "Code must not be null")
    @Size(max = 4, message = "Code must have length 6 chars")
    @Column
    private String code;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Student> students;

    //Constructor
    public Course(String name, String code){
        this.name = name;
        this.code = code;
    }

    //Constructor
    public Course(){}

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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public List<Student> getStudents(){
        return students;
    }
}
