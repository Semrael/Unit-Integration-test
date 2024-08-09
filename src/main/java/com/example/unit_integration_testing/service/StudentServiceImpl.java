package com.example.unit_integration_testing.service;

import com.example.unit_integration_testing.Entity.Student;
import com.example.unit_integration_testing.repository.StudentRepository;
import com.example.unit_integration_testing.exceptions.StudentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{
    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        Optional<Student> studentOptional=studentRepository.findById(id);
        if(studentOptional.isPresent()){
            return studentOptional.get();
        }
        throw new StudentException("Student  with given id is not exist: " ,HttpStatus.NOT_FOUND);
    }

    @Override
    public Student findByTckn(String tckn) {
        return studentRepository.findByTckn(tckn);
    }

    @Override
    public List<Student> findByFirstName(String firstName) {
        return studentRepository.findByFirstName(firstName);
    }

    @Override
    public Student save(Student student) {
       Student foundStudent= studentRepository.findByTckn(student.getTckn());
       if(foundStudent!=null){
           throw new StudentException("Student with given tckn already exist:",HttpStatus.BAD_REQUEST);
       }
        return studentRepository.save(student);
    }

    @Override
    public Student remove(Long id) {
        Student foundStudent =findById(id);
        studentRepository.delete(foundStudent);
        return foundStudent ;
    }
}
