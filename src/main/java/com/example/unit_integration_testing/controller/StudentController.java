package com.example.unit_integration_testing.controller;

import com.example.unit_integration_testing.Entity.Student;


import com.example.unit_integration_testing.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public  List<Student> findAll(){
        return studentService.findAll();
    }


    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Student findById(@PathVariable Long id){
        return studentService.findById(id);
    }
    @GetMapping("/tckn/{tckn}")
    @ResponseStatus(HttpStatus.OK)
    public Student findByTckn(@PathVariable String tckn){
        return studentService.findByTckn(tckn);
    }
    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    List<Student> findByFirstName(@PathVariable String name){
        return studentService.findByFirstName(name);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Student save(@RequestBody Student student){
       return studentService.save(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Student remove(@PathVariable long id){
        return studentService.remove(id);
    }
}
