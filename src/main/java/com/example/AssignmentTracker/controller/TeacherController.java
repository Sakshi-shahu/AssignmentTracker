package com.example.AssignmentTracker.controller;

import com.example.AssignmentTracker.dto.TeacherResponseDto;
import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/t")
@RequiredArgsConstructor
public class TeacherController {


    private  final TeacherService teacherService;


    @PostMapping("/add")
    public ResponseEntity<Teacher> createTeacher(@RequestBody TeacherResponseDto  teacherResponseDto){
       return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.addTeacher(teacherResponseDto));
    }


    @GetMapping("/get/{id}")
    public  ResponseEntity<Teacher> getTeacher(@PathVariable long id){
        return  ResponseEntity.status(HttpStatus.FOUND).body(teacherService.getTeacher(id));
    }



    @GetMapping("/allTeacher")
    public  ResponseEntity<List<Teacher>> getAllTeacher(){
        List<Teacher> allTeachers = teacherService.getAllTeachers();
        return ResponseEntity.of(Optional.ofNullable(allTeachers));
    }




    @PutMapping("/update/{id}")
    public  ResponseEntity<Teacher> updateTeacher( @PathVariable long id,  @RequestBody TeacherResponseDto teacher ){
        return  ResponseEntity.ok(teacherService.updateTeacher(id, teacher));
    }


    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<String> deleteTeacher( @PathVariable long id ){
        teacherService.deleteTeacher(id);
        return  ResponseEntity.ok("teacher deleted successfully");
    }


}
