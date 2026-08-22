package com.example.AssignmentTracker.service;


import com.example.AssignmentTracker.Dto.RequestDto;
import com.example.AssignmentTracker.Exception.StudentNotFoundException;
import com.example.AssignmentTracker.entity.Role;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;


    public Student addStudent(RequestDto requestDto ){
        Student student=Student.builder().name(requestDto.getName())
                .email(requestDto.getEmail())
                .phone(requestDto.getPhone())
                .course(requestDto.getCourse())
                .role(Role.STUDENT)
                .build();
        return  studentRepository.save(student);

    }



  public Student getStudent(Long id){
        return studentRepository.findById(id).orElseThrow(()->
                new StudentNotFoundException("student not found with this id"+id));

  }

  public List<Student> getAllStudent(){
    return studentRepository.findAll();

    }

    public Student updateStudent(Student student,Long id){
        Student exsistStudent=studentRepository.findById(id).orElseThrow(()->
                new StudentNotFoundException("student not found with this id"+id));

        exsistStudent.setName(student.getName());
        exsistStudent.setEmail(student.getEmail());
        exsistStudent.setCourse(student.getCourse());

        return studentRepository.save(exsistStudent);
    }


    public void deleteStudent(Long id){
        Student student=studentRepository.findById(id).orElseThrow(()->
                new StudentNotFoundException("student not found with this id"+id));
       studentRepository.delete(student);
    }

    public void deleteAllStudent(){
      studentRepository.deleteAll();
    }

}
