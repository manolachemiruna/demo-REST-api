package com.example.demo.services;

import com.example.demo.entities.Student;
import com.example.demo.entities.Subject;
import com.example.demo.exceptions.CustomException;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public StudentService(StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Student> getAllStudents(Boolean isSorted){
        List<Student> students;
                if(isSorted)students = studentRepository.findAllByOrderByLastnameAsc();
                else students = studentRepository.findAll();
        return students;
    }

    public Student geStudentById(Integer id){
        Optional<Student> studentOptional =  studentRepository.findById(id);
        return studentOptional.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"There is no student with id : " + id));
    }

    public Student addStudent(Student student){
        Student savedStudent = studentRepository.save(student);
        return savedStudent;
    }

    public Student update(Student student){
        Optional<Student> entity = studentRepository.findById(student.getId());
        entity.orElseThrow((() -> new RuntimeException("Student with id: "+ student.getId() + " not found")));
        Student existingStudent = entity.get();
        existingStudent.setFirstname(student.getFirstname());
        existingStudent.setLastname(student.getLastname());
        checkIfSubjectsExist(student.getSubjects());
        existingStudent.setSubjects(new HashSet<>(subjectRepository.findAllById(student.getSubjectsIds())));
        Student savedStudent = studentRepository.save(existingStudent);
        return savedStudent;
    }

    private void checkIfSubjectsExist(Set<Subject> subjects){
        subjects.stream().map(subject -> subject.getId()).forEach(id -> subjectRepository
                        .findById(id).orElseThrow(() ->new CustomException(HttpStatus.NOT_FOUND,"There is no subject with id : " + id)));

    }
}