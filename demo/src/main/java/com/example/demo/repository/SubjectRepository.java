package com.example.demo.repository;

import com.example.demo.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {

     List<Subject> findAll();
     List<Subject> findAllByTeacherName(String teacherName);

}