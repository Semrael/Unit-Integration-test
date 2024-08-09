package com.example.unit_integration_testing.service;

import com.example.unit_integration_testing.Entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(Long id);
    Student findByTckn(String tckn);
    List<Student> findByFirstName(String firstName);
    Student save(Student student);
    Student remove(Long id);
}
