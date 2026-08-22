package com.example.AssignmentTracker.controller;


import com.example.AssignmentTracker.Dto.RequestDto;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.response.ApiResponse;
import com.example.AssignmentTracker.service.IdempotencyService;
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
public class StudentControllerss {

    private final  StudentService studentService;
    private final IdempotencyService idempotencyService;


    @PostMapping("/addstudent")
    public ResponseEntity<ApiResponse<Student>> addStudent(@RequestHeader("Idem-key") String IdempotencyKey
            ,@Valid  @RequestBody RequestDto requestDto){

        if(idempotencyService.isProceed(IdempotencyKey)){
            Long studentId=idempotencyService.get(IdempotencyKey);
            Student existingId=studentService.getStudent(studentId);
            ApiResponse<Student> response = ApiResponse.<Student>builder()
                    .success(true).message("Already Student Exits").data(existingId).build();
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }

        Student savedStudent =studentService.addStudent(requestDto);
        idempotencyService.put(IdempotencyKey, savedStudent.getId());
        ApiResponse<Student> response=ApiResponse.<Student>builder().success(true)
                .message("flight created successfully")
                .data(savedStudent)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

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
