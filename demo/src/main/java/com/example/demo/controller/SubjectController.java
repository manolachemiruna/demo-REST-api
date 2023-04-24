package com.example.demo.controller;

import com.example.demo.entities.Subject;
import com.example.demo.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping(value="/allSubjects")
    public List<Subject> getAllCourses() {
        return subjectService.getAllSubjects();
    }

    @PostMapping(value="/newSubject")
    public ResponseEntity<Subject> addStudent(@RequestBody Subject subject){
        Subject addedCSubject=subjectService.addSubject(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCSubject);
    }

    @GetMapping(value="/allSubjectsByTeacher")
    public List<Subject> getAllCoursesByUserId(@RequestParam String teacherName){
        return subjectService.getAllSubjectsByTeacherName(teacherName);
    }



}
