package com.example.AssignmentTracker.controller;


import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final  StudentService studentService;


    @PostMapping("/addstudent")
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student){
     Student savedStudent =studentService.addStudent(student);
     return  ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);

    }

    @GetMapping("/getstudent/{id}")
    public ResponseEntity<Student> get( @PathVariable Long id){
        Student getStudent=studentService.getStudent(id);
        return  ResponseEntity.ok(getStudent);

    }

    @GetMapping("/getAllStudent")
    public ResponseEntity<List<Student>> getAll(){
       List<Student>  student=studentService.getAllStudent();
       return  ResponseEntity.ok(student);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student student, @PathVariable  Long id){
        Student updateStudent=studentService.updateStudent(student,id);
        return  ResponseEntity.ok(updateStudent);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable long id){
         studentService.deleteStudent(id);
         return  ResponseEntity.ok("student delete successfully");

    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllStudent(){
        studentService.deleteAllStudent();
        return  ResponseEntity.ok("delete all student Successfully");
    }
}
