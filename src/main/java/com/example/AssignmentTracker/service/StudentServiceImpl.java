package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Dto.StudentRequest;
import com.example.AssignmentTracker.Dto.StudentResponse;
import com.example.AssignmentTracker.Exception.DuplicateResourceException;
import com.example.AssignmentTracker.entity.Role;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.Exception.ResourceNotFoundException;
import com.example.AssignmentTracker.repository.StudentRepository;
import com.example.AssignmentTracker.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;

    @Override
    public StudentResponse addStudent(StudentRequest request) {
        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }

        Student student = modelMapper.map(request, Student.class);
        student.setCreatedAt(LocalDateTime.now());
        student.setRole(Role.STUDENT);
        Student savedStudent =studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentResponse.class);
    }


    @Override
    public Student updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (student.getEmail().equals(request.getEmail())) {
            throw new DuplicateResourceException(
                    "New email must be different from previous email"
            );
        }

        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());
        student.setRole(Role.STUDENT);
        student.setUpdatedAt(LocalDateTime.now());
        return studentRepository.save(student);
    }


    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        studentRepository.delete(student);
    }

    @Override
    public StudentResponse getStudentProfile(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setCourse(student.getCourse());
        return response;
    }
}
