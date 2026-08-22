package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Exception.TeacherNotFoundException;
import com.example.AssignmentTracker.dto.TeacherResponseDto;
import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherService {

    private  final TeacherRepository teacherRepository;
private  final ModelMapper mapper;

    public  Teacher addTeacher(TeacherResponseDto responseDto){
        Teacher teacher = mapper.map(responseDto, Teacher.class);
        return   teacherRepository.save(teacher);
    }


    public  Teacher getTeacher(Long id){
        return teacherRepository.findById(id).get();
    }



    public List<Teacher> getAllTeachers(){
        return  teacherRepository.findAll();
    }



    public  Teacher updateTeacher(Long id, TeacherResponseDto teacher ){
        Teacher t1 = teacherRepository.findById(id).orElseThrow(()-> new TeacherNotFoundException("teacher not found with id :  "+ id));
        mapper.map(teacher, t1);
      return    teacherRepository.save(t1);
    }


    public  void deleteTeacher(Long id){
        teacherRepository.deleteById(id);
    }



}
