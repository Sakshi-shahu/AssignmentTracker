package com.example.AssignmentTracker.service;


import com.example.AssignmentTracker.Exception.StudentNotFoundException;
import com.example.AssignmentTracker.config.MapperConfig;
import com.example.AssignmentTracker.dto.StudentResponseDto;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.repository.StudentRepository;
import com.example.AssignmentTracker.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
private  final TeacherRepository teacherRepository;
private  final ModelMapper mapper;

    public Student addStudent(StudentResponseDto student, Long t_id){
        Teacher teacher = teacherRepository.findById(t_id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Teacher not found with id: " + t_id
                        ));
          Student st=    mapper.map(student, Student.class);
        st.setTeacher(teacher);

        return studentRepository.save(st);

    }


  public Student getStudent(Long id){
        return studentRepository.findById(id).orElseThrow(()->
                new StudentNotFoundException("student not found with this id"+id));

  }

  public List<Student> getAllStudent(){
    return studentRepository.findAll();

    }

    public Student updateStudent(StudentResponseDto student,Long id){
        Student exsistStudent=studentRepository.findById(id).orElseThrow(()->
                new StudentNotFoundException("student not found with this id"+id));
mapper.map(student, exsistStudent);


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
