package com.example.unit_integration_testing.repository;

import com.example.unit_integration_testing.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
        @Query("select s from Student s where s.tckn = :tckn ")
        Student findByTckn(String tckn);


        @Query("select s from Student s where s.firstName= :firstName")
        List<Student> findByFirstName(String firstName);
    }

