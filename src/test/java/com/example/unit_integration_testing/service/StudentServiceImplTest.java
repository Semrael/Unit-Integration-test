package com.example.unit_integration_testing.service;

import com.example.unit_integration_testing.Entity.Student;
import com.example.unit_integration_testing.exceptions.StudentException;
import com.example.unit_integration_testing.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
//Mock=> Mockito Api

@SpringBootTest
@ExtendWith(MockitoExtension.class)

class StudentServiceImplTest {

    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp(){studentService=new StudentServiceImpl(studentRepository);}

    @Test
    void findAll() {
        Student student=new Student();
        student.setEmail("test@gmail.com");
        student.setTckn("11111111111");
        student.setFirstName("semra");
        student.setLastName("elçelik");

        Student student1=new Student();
        student1.setEmail("test@gmail.com");
        student1.setTckn("11111111111");
        student1.setFirstName("semra");
        student1.setLastName("elçelik");

        List<Student> students= Arrays.asList(student,student1);


        //Stubbing
        given(studentRepository.findAll()).willReturn(students);

        //Testi çalıştırma
       List<Student> foundStudents= studentService.findAll();

       //Doğrulama
        assertIterableEquals(students,foundStudents);
        verify(studentRepository).findAll();


    }

    @Test
    void findById() {
        Student student =new Student();
        student.setId(1L);
        student.setEmail("test@gmail.com");
        student.setTckn("11111111111");
        student.setFirstName("semra");
        student.setLastName("Elçelik");
        studentService.save(student);

        // Stubbing
        //studentRepository.findById(1L) metodunun çağrıldığında, belirtilen student nesnesini döndüreceği belirtiliyor.
        // Bu, test sırasında repository'nin gerçek bir veritabanı yerine bu veriyi döndüreceği anlamına gelir.
        given(studentRepository.findById(1L)).willReturn(Optional.of(student));

        // Testi Çalıştırma
        Student foundStudent=studentService.findById(1L);
        // Bulunan öğrencinin null olmadığını doğrular.
        assertNotNull(foundStudent);
        //Bulunan öğrencinin id'sinin 1 olduğunu doğrular.
        assertEquals(1L,foundStudent.getId());
        //findById(1L) metodunun gerçekten çağrıldığını doğrular.
        verify(studentRepository).findById(1L);





    }

    @Test
    void findByTckn() {

        //Yeni bir Student nesnesi oluştur özellikler ata
        Student student = new Student();
        student.setId(1L);
        student.setTckn("111111111111");
        student.setFirstName("Semra");
        student.setLastName("Elçelik");


        //Mock studentRepository nesnesi için tckn ye göre arama yapıldığında null döndğrmesi sağlanıyor
        given(studentRepository.findByTckn("111111111111")).willReturn(null);
        //Mock studentRepository nestesi için student kaysedildiğinde  aynı student ın sönmesi sağlanıyo
        given(studentRepository.save(student)).willReturn(student);
        //Mock studentRepository nesnesi için tckn ye göre arama yapıldığında student nesnesi döndğrmesi sağlanıyor
        given(studentRepository.findByTckn("111111111111")).willReturn(student);


        //Öğrenci nesnesi veritabanına kaydediliyor
        studentRepository.save(student);

        //tckn ye göre öğrenci bulunup foundStudenta kaydediliyor
        Student foundStudent = studentService.findByTckn("111111111111");

        //Bulunan öğrencinin null olmadığından emin oluyor
        assertNotNull(foundStudent);
        //Bulunan öğrencinin tckn sinin beklenen depğerle eşleştiği kontrol ediliyor
        assertEquals("111111111111", foundStudent.getTckn());
        //studentRepository.findByTckn("11111111111") metodunun çağrıldığından emin olununyor
        verify(studentRepository).findByTckn("111111111111");
    }

    @Test
    void findByFirstName() {
        Student student=new Student();
        student.setId(1L);
        student.setFirstName("Semra");
        student.setLastName("Elçelik");
        Student student1=new Student();
        student1.setId(2L);
        student1.setFirstName("Semra");
        student1.setLastName("Elçelik");
        Student student2=new Student();
        student2.setId(3L);
        student2.setFirstName("Esra");
        student2.setLastName("Elçelik");

        //öğreencileri listeye ekleme
        List<Student> students=Arrays.asList(student,student1);
        //Stubbing
        given(studentRepository.findByFirstName("Semra")).willReturn(students);

        //Testi çalıştırma
        List<Student> foundStdents=studentService.findByFirstName("Semra");

        //Doğrulama
        assertIterableEquals(students,foundStdents);

        //indByFirstName metodunun gerçekten çağrıldığını doğrulama
        verify(studentRepository).findByFirstName("Semra");

    }
    @Test
    void save(){
        Student student =new Student();
        student.setEmail("test@gmail.com");
        student.setTckn("11111111111");
        student.setFirstName("semra");
        student.setLastName("Elçelik");
        studentService.save(student);
        verify(studentRepository).save(student);
    }

    @Test
    void canNotsave() {
        Student student =new Student();
        student.setEmail("test@gmail.com");
        student.setTckn("11111111111");
        student.setFirstName("semra");
        student.setLastName("Elçelik");

        //Stubiing
        given(studentRepository.findByTckn("11111111111")).willReturn(student);

        assertThatThrownBy(()->studentService.save(student))
                .isInstanceOf(StudentException.class)
                .hasMessageContaining("Student with given tckn already exist:");

        verify(studentRepository,never()).save(student);



    }

    @Test
    void remove() {
        Student student =new Student();
        student.setId(1L);
        student.setEmail("test@gmail.com");
        student.setTckn("11111111111");
        student.setFirstName("Semra");
        student.setLastName("Elçelik");

        //Stubbing
        given(studentRepository.findById(Long.valueOf(1L))).willReturn(Optional.of(student));

        Student removeStudent=studentService.remove(student.getId());
        verify(studentRepository).delete(student);
        assertEquals("Semra",removeStudent.getFirstName());


    }
}