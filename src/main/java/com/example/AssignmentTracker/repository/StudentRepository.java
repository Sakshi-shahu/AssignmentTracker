package com.example.AssignmentTracker.repository;

import com.example.AssignmentTracker.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findByTeacherId(Long teacherId);


    List<Student> findByCourse(String course);

}
