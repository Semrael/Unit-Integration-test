package com.example.unit_integration_testing.repository;

import com.example.unit_integration_testing.Entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//JUnit5 => Jupiter api
//unit test sitem gaplerini önceden tespit edebiliriz
//DEV => veritabanları üzerinde test koşabilirsin.(DEV DB)
//TEST => veritabanları üzerinde test koşabilirsin.(TEST DB)
//PREPROD=> veritabanı teste bakar. Kodlar proda gidecek kodlardı
//PROD => veritabanı üzerinde test koşulmaz

// Bu anotasyon, Spring Boot test ortamını başlatır ve Spring context'i yükler.
@SpringBootTest
class StudentRepositoryTest {
    private StudentRepository studentRepository;

    // Constructor injection: Spring, StudentRepository bağımlılığını enjekte eder.
    @Autowired
    public StudentRepositoryTest(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    // Test veritabanına yeni bir öğrenci eklemek için kullanılan yardımcı method.
    private void createStudent(String tckn){
        Student student =new Student();
        student.setTckn(tckn);
        student.setFirstName("Semra");
        student.setLastName("Elçelik");
        student.setEmail("test@gmail.com");

        // Eğer öğrenci zaten varsa, yeniden eklenmesini önlemek için kontrol yapar.
       Student foundStudent= studentRepository.findByTckn(tckn);
       if(foundStudent==null){
           studentRepository.save(student);
       }

    }

    // Her testten önce çalışır, test veritabanını başlangıç durumuna getirir.
    @BeforeEach
    void setUp(){
        createStudent("11111111111");
    }
    // Her testten sonra çalışır, test veritabanını temizler.
    @AfterEach
    void tearDown(){
        studentRepository.deleteAll();
    }


    @DisplayName("Can find student by tckn")
    @Test
    void findByTckn() {
       Student foundStudent= studentRepository.findByTckn("11111111111");

        // Öğrenciyi tckn ile bulur ve doğrular.
        assertNotNull(foundStudent);

        // Öğrencinin isim ve email bilgilerini doğrular.
        assertEquals("Semra",foundStudent.getFirstName());
        assertEquals("test@gmail.com",foundStudent.getEmail());
    }
    @DisplayName("Can't find student by tckn")
    @Test
    void findByTcknFail(){
        // Var olmayan bir tckn ile öğrenciyi bulmaya çalışır.
       Student foundStudent= studentRepository.findByTckn("11111111112");

       // Öğrencinin null olduğunu doğrular.
       assertNull(foundStudent);

    }




    @DisplayName("Can find student by firstName")
    @Test
     void findByFirstName() {
        // İsim ile öğrenci arar ve doğrular.
        List<Student> students= studentRepository.findByFirstName("Semra");
        assertEquals(1,students.size());
        assertEquals("11111111111",students.get(0).getTckn());

        // Yeni bir öğrenci ekler ve tekrar arama yapar.
        createStudent("11111111113");
        students=studentRepository.findByFirstName("Semra");

        // Öğrenci listesinin güncellenmiş boyutunu doğrular.
        assertEquals(2,students.size());
    }
}