package com.java.prueba.entry.entities;

import java.util.List;

/**
 * Interface for implement Student class.
 */
public interface IStudent {
    public long getId();
    public void setId(long id);
    public String getRut();
    public void setRut(String rut);
    public String getName();
    public void setName(String name);
    public String getLastName();
    public void setLastName(String lastName);
    public int getAge();
    public void setAge(int age);
    public ICourse getICourse();
    public void setICourse(ICourse course);
}
