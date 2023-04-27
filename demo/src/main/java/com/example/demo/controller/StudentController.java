package com.example.demo.controller;

import com.example.demo.entities.Student;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller = layer-ul care interactioneaza cu frontend-ul
//aici ajung request-urile trimise de frontend

@RestController //adnotare folosita pentru a marca ca aceasta claa e un Controller
@RequestMapping("/api/student")
public class StudentController {

    //controller-ul apeleaza mai departe un service layer
    //aici folosim adnotare @Autowired prin care marcam faptul ca Spring
    //se va ocupa sa injecteze aceasta dependinta in clasa nostra de controller
    //Putem folosi @Autowired doar daca obiectul pe care vrem sa il injecteze Spring
    //e un bean managed by Spring (e adnotat cu @Service/@Repository/@Component/@Bean)
    //puteti cauta mai multe despre bean-uri in documentatia de la Spring Boot

    @Autowired
    StudentService studentService;

    @PostMapping(value="/newStudent") //POST echivalentul unui insert/create
    //asteapta un body(@RequestBody) pentru a stii ce obiect va fi creat
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student result = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @GetMapping(value="/allStudents") //GET e echivalentul unui SELECT
    //@RequestParam -> /api/student/allStudents?isSorted=true
    //ii putem seta un default value, astfel ca daca apelam doar /api/student/allStudents, va fi folosita
    //acea valoare default
    public List<Student> getAllStudents(@RequestParam(defaultValue = "false", required = false) boolean isSorted){
        return studentService.getAllStudents(isSorted);
    }

    @GetMapping(value="/{myId}")
    //PathVariable -> /api/student/123
    public Student getStudent(@PathVariable("myId") Integer id) {
        return studentService.geStudentById(id);
    }


    @PutMapping(value="/update") //PUT este echivalentul unui update
    //la fel ca POST asteapta un @RequestBody pentru a stii carui obiect ii face update si care
    //sunt noile fields
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student result = studentService.update(student);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
