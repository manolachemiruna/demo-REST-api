package com.example.demo.services;

import com.example.demo.entities.Student;
import com.example.demo.entities.Subject;
import com.example.demo.exceptions.CustomException;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service //marcheaza layer-ul de Service,
//aici apeleam repository-ul care e primul layer "peste" baza de date
//si putem face diverse operatii peste obiectele aduse de repository
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    //putem injecta dependintele si in mod clasic, folosind un contructor in loc de @Autowired
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

    //daca incercam sa cautam un student dupa un id care nu exista in baza de date
    //vom primi 500 Interval Server Error, daca acest mesaj ajunge in frontend
    //user-ul nu va stii ce s-a intamplat, de aceea vom folosi Custom Exceptions si exception handler
    //astfel ca pentru un id care nu exista vom avea status 404 Not Found si un mesaj mai clar decat Internal Server Error
    //VEZI exceptions package
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