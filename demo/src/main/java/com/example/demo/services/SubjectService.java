package com.example.demo.services;

import com.example.demo.entities.Subject;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;

    public Subject addSubject(Subject subject){
        Subject savedSubject = subjectRepository.save(subject);
        return savedSubject;
    }

    public List<Subject> getAllSubjects(){
        List<Subject> subjects = subjectRepository.findAll();
        return subjects;
    }

    public List<Subject> getAllSubjectsByTeacherName(String teacherName){
        List<Subject> subjects=subjectRepository.findAllByTeacherName(teacherName);
        return subjects;
    }

}
