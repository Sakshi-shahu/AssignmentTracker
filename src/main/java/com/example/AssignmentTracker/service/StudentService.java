package com.example.AssignmentTracker.service;


import com.example.AssignmentTracker.Exception.studentNotFoundException;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;


    public Student addStudent(Student student){
        return  studentRepository.save(student);

    }


  public Student getStudent(Long id){
        return studentRepository.findById(id).orElseThrow(()->
                new studentNotFoundException("student not found with this id"+id));

  }

  public List<Student> getAllStudent(){
    return studentRepository.findAll();

    }

    public Student updateStudent(Student student,Long id){
        Student exsistStudent=studentRepository.findById(id).orElseThrow(()->
                new studentNotFoundException("student not found with this id"+id));

        exsistStudent.setName(student.getName());
        exsistStudent.setEmail(student.getEmail());
        exsistStudent.setCourse(student.getCourse());

        return studentRepository.save(exsistStudent);
    }


    public void deleteStudent(Long id){
        Student student=studentRepository.findById(id).orElseThrow(()->
                new studentNotFoundException("student not found with this id"+id));
       studentRepository.delete(student);
    }

    public void deleteAllStudent(){
      studentRepository.deleteAll();
    }

}
