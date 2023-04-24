package com.example.demo.repository;

import com.example.demo.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface StudentRepository extends JpaRepository<Student, Integer> {

        List<Student> findAll();
        List<Student> findAllByOrderByLastnameAsc();

    }

