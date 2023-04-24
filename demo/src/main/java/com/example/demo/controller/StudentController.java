package com.example.demo.controller;

import com.example.demo.entities.Student;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping(value="/allStudents")
    public List<Student> getAllStudents(@RequestParam(defaultValue = "false", required = false) boolean isSorted){
        return studentService.getAllStudents(isSorted);
    }

    @GetMapping(value="/{myId}")
    public Student getStudent(@PathVariable("myId") Integer id) {
        return studentService.geStudentById(id);
    }


    @PostMapping(value="/newStudent")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student result = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(value="/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student result = studentService.update(student);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
