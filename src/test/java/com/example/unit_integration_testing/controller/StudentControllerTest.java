package com.example.unit_integration_testing.controller;

import com.example.unit_integration_testing.Entity.Student;
import com.example.unit_integration_testing.service.StudentService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//Integration Test=>İntegraston testi Controller testi için birden fazla parçayı bir araya getirdiğimizde mantıklı sonuç elde  edebiliriz

@WebMvcTest
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;

    @Test
    void findAll() throws Exception {
      //Öğrenci listesi oluştur
      Student student1= new Student();
      student1.setId(1L);
      student1.setEmail("semra@gmail.com");
      student1.setTckn("11111111111");
      student1.setFirstName("Semra");
      student1.setLastName("Elçelik");

        Student student2= new Student();
        student2.setId(2L);
        student2.setEmail("zehra@gmail.com");
        student2.setTckn("11111111112");
        student2.setFirstName("Zehra");
        student2.setLastName("bilmiyorum");

        //öğrencileri listesi oluştur
        List<Student> students= Arrays.asList(student1,student2);

        // Stubbing--- studentService.findAll() çağrıldığında öğrenci listesini döndürece
        when(studentService.findAll()).thenReturn(students);
       // MockMvc kullanılarak HTTP GET isteği yapılır ve yanıt kontrol edilir.
        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())// Yanıt durumunun 200 OK olduğundan emin olunur.
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))// Listenin uzunluğunun 2 olduğundan emin olunur.
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L)) // İlk öğrencinin ID'sinin 1L olduğundan emin olunur.
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Semra"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Zehra"));


         verify(studentService).findAll();


    }

    @Test
    void findById() throws Exception{
        //Öğrenci listesi oluştur
        Student student1= new Student();
        student1.setId(1L);
        student1.setEmail("semra@gmail.com");
        student1.setTckn("11111111111");
        student1.setFirstName("Semra");
        student1.setLastName("Elçelik");

        studentService.save(student1);
        when(studentService.findById(1L)).thenReturn(student1);
        mockMvc.perform(get("/student/id/{id}",1))
                .andExpect(status().isOk())// Yanıt durumunun 200 OK olduğundan emin olunur.
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Semra"));

        verify(studentService).findById(1L);


    }

    @Test
    void findByTckn() throws Exception{
        Student student1= new Student();
        student1.setId(1L);
        student1.setEmail("semra@gmail.com");
        student1.setTckn("11111111111");
        student1.setFirstName("Semra");
        student1.setLastName("Elçelik");

        when(studentService.findByTckn("11111111111")).thenReturn(student1);

        mockMvc.perform(get("/student/tckn/{tckn}","11111111111"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tckn").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tckn").value("11111111111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("semra@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Semra"));

        verify(studentService).findByTckn("11111111111");


    }

    @Test
    void findByFirstName() throws Exception{
        Student student1= new Student();
        student1.setId(1L);
        student1.setEmail("semra@gmail.com");
        student1.setTckn("11111111111");
        student1.setFirstName("Semra");
        student1.setLastName("Elçelik");

        Student student2= new Student();
        student2.setId(2L);
        student2.setEmail("semra@gmail.com");
        student2.setTckn("11111111112");
        student2.setFirstName("Semra");
        student2.setLastName("bilmiyorum");

        //öğrencileri listesi oluştur
        List<Student> students= Arrays.asList(student1,student2);

        when(studentService.findByFirstName("Semra")).thenReturn(students);

        mockMvc.perform(get("/student/name/{name}","Semra"))
                .andExpect(status().isOk()) // Yanıt durumunun 200 OK olduğundan emin olun
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2)) // Listede 2 öğe olduğundan emin olun
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L)) // İlk öğrencinin ID'sinin 1L olduğundan emin olun
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tckn").value("11111111111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Semra"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].tckn").value("11111111112"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Semra"));



        verify(studentService).findByFirstName("Semra");

    }

    @Test
    void save() throws Exception {
       Student student=new Student();
          student.setId(1L);
        student.setTckn("11111111111");
        student.setEmail("sem@gmail.com");
        student.setFirstName("Semra");
        student.setLastName("Elçelik");

        // Stubbing: studentService.save(student) çağrıldığında, oluşturulan student nesnesi döndürülecek.
        when(studentService.save(student)).thenReturn(student);

        // MockMvc kullanılarak HTTP POST isteği yapılır ve yanıt kontrol edilir.
        mockMvc.perform(post("/student/") // /student/ endpoint'ine POST isteği gönderilir.
                        .contentType(MediaType.APPLICATION_JSON) // İçerik tipi JSON olarak belirtiliyor.
                        .content(jsonToString(student)) // Gönderilecek JSON içeriği ayarlanıyor.
                        .accept(MediaType.APPLICATION_JSON)) // Yanıt olarak JSON bekleniyor.
                .andExpect(status().isCreated()) // Yanıt durumunun 201 CREATED olduğundan emin olunur.
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists()) // Yanıtta 'id' alanının var olduğundan emin olunur.
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Semra")); // Yanıtta 'firstName' alanının "Semra" olduğundan emin olunur.

        // save metodunun gerçekten çağrıldığını doğrular.
        verify(studentService).save(student);




    }
//
//    @Test
//    void save() throws Exception {
//        // Yeni bir öğrenci nesnesi oluşturun
//        Student student = new Student();
//        student.setTckn("11111111111");
//        student.setEmail("sem@gmail.com");
//        student.setFirstName("Semra");
//        student.setLastName("Elçelik");
//
//        // Kaydedildikten sonra veritabanının atayacağı ID'yi simüle edin
//        Student savedStudent = new Student();
//        savedStudent.setId(1L); // Simüle edilen ID değeri
//        savedStudent.setTckn("11111111111");
//        savedStudent.setEmail("sem@gmail.com");
//        savedStudent.setFirstName("Semra");
//        savedStudent.setLastName("Elçelik");
//
//        // Mocklama
//        when(studentService.save(student)).thenReturn(savedStudent);
//
//        // MockMvc kullanarak POST isteği yapın ve yanıtı kontrol edin
//        mockMvc.perform(post("/student/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonToString(student))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated()) // Yanıt durumunun 201 CREATED olduğundan emin olun
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L)) // ID'nin doğru olduğundan emin olun
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Semra")); // firstName alanının doğru olduğundan emin olun
//
//        // `save` metodunun gerçekten çağrıldığını doğrulayın
//        verify(studentService).save(student);
//    }

    @Test
    void remove() throws Exception {

        Student student = new Student();
        student.setId(1L);
        student.setTckn("11111111111");
        student.setEmail("sem@gmail.com");
        student.setFirstName("Semra");
        student.setLastName("elçelik");

        // Stubbing: studentService.findById(1L) çağrıldığında, oluşturulan öğrenci nesnesi döndürülecek.
        when(studentService.findById(1L)).thenReturn(student);

        // Stubbing: studentService.remove(student.getId()) çağrıldığında, oluşturulan öğrenci nesnesi döndürülecek.
        when(studentService.remove(student.getId())).thenReturn(student);

        // MockMvc kullanılarak HTTP DELETE isteği yapılır ve yanıt kontrol edilir.
        mockMvc.perform(delete("/student/{id}", 1)) // /student/1 endpoint'ine DELETE isteği gönderilir.
                .andExpect(status().isOk()) // Yanıt durumunun 200 OK olduğundan emin olunur.
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists()) // Yanıtta 'id' alanının var olduğundan emin olunur.
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("sem@gmail.com")); // Yanıtta 'email' alanının "sem@gmail.com" değerine sahip olduğundan emin olunur.

        // remove metodunun 1L argümanıyla çağrıldığını doğrular.
        verify(studentService).remove(1L);
    }

    public static String jsonToString(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
            }
        }
    }
