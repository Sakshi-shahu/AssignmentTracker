package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Dto.LoginDto;
import com.example.AssignmentTracker.Dto.ResponseLogin;
import com.example.AssignmentTracker.Dto.StudentRequest;
import com.example.AssignmentTracker.Dto.StudentResponse;
import com.example.AssignmentTracker.Exception.DuplicateResourceException;
import com.example.AssignmentTracker.customjwt.JwtService;
import com.example.AssignmentTracker.entity.Role;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.Exception.ResourceNotFoundException;
import com.example.AssignmentTracker.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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




    public ResponseLogin login(@NonNull LoginDto loginDto) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken
                    .unauthenticated(loginDto.getEmail(), loginDto.getPassword()));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username password");

        }
        Student user=studentRepository.findByEmail(loginDto.getEmail()).orElseThrow(()->
                new UsernameNotFoundException("username not found"));
        ResponseLogin responseDto = modelMapper.map(user, ResponseLogin.class);
        responseDto.setToken(jwtService.generateToken(user.getEmail(), user.getPassword()));
        return responseDto;

    }
}
