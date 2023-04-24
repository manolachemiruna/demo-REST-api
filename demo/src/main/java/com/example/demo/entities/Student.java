package com.example.demo.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table()
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @ManyToMany
    @JoinTable(name="student_subject",joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Subject> getSubjects() {
        return subjects != null ? subjects : new HashSet<>();
    }

    public List<Integer> getSubjectsIds() {
        return subjects != null ? subjects.stream().map(Subject::getId).collect(Collectors.toList()) : new ArrayList<>();
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
