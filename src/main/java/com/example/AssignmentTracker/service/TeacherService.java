package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherService {

    private  final TeacherRepository teacherRepository;


    public  Teacher addTeacher(Teacher teacher){
      return   teacherRepository.save(teacher);
    }


    public  Teacher getTeacher(Long id){
        return teacherRepository.findById(id).get();
    }



    public List<Teacher> getAllTeachers(){
        return  teacherRepository.findAll();
    }



    public  Teacher updateTeacher(Long id, Teacher teacher ){
        Teacher t1 = teacherRepository.findById(id).get();
        t1.setName(teacher.getName());
        t1.setSubject(teacher.getSubject());
      return    teacherRepository.save(t1);
    }


    public  void deleteTeacher(Long id){
        teacherRepository.deleteById(id);
    }



}
