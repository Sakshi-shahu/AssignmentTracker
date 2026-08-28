package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Exception.AssignmentNotFoundException;
import com.example.AssignmentTracker.Exception.AssignmentStudentNotFoundException;
import com.example.AssignmentTracker.Exception.StudentNotFoundException;
import com.example.AssignmentTracker.Exception.UnauthorizedException;
import com.example.AssignmentTracker.dto.AssignmentStudentResponseDto;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.AssignmentStudent;
import com.example.AssignmentTracker.entity.Student;
import com.example.AssignmentTracker.repository.AssignmentRepository;
import com.example.AssignmentTracker.repository.AssignmentStudentRepository;
import com.example.AssignmentTracker.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentStudentServiceImpl
        implements AssignmentStudentService {

    private final AssignmentStudentRepository
            assignmentStudentRepository;

    private final AssignmentRepository assignmentRepository;

    private final StudentRepository studentRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public List<AssignmentStudentResponseDto> assignStudents(
            Long assignmentId,
            List<Long> studentIds,
            Long teacherId
    ) {

        Assignment assignment =
                assignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new AssignmentNotFoundException(
                                        "Assignment not found with id: "
                                                + assignmentId
                                )
                        );

        if (!assignment.getTeacher()
                .getId()
                .equals(teacherId)) {

            throw new UnauthorizedException(
                    "You cannot assign students to this assignment"
            );
        }

        List<AssignmentStudent> mappings =
                new ArrayList<>();

        for (Long studentId : studentIds) {

            Student student =
                    studentRepository.findById(studentId)
                            .orElseThrow(() ->
                                    new StudentNotFoundException(
                                            "Student not found with id: "
                                                    + studentId
                                    )
                            );

            if (assignmentStudentRepository
                    .existsByAssignmentIdAndStudentId(
                            assignmentId,
                            studentId
                    )) {

                continue;
            }

            AssignmentStudent mapping =
                    new AssignmentStudent();

            mapping.setAssignment(assignment);
            mapping.setStudent(student);
            mapping.setAssignedAt(LocalDateTime.now());

            mappings.add(mapping);
        }

        return assignmentStudentRepository
                .saveAll(mappings)
                .stream()
                .map(entity ->
                        modelMapper.map(
                                entity,
                                AssignmentStudentResponseDto.class
                        )
                )
                .toList();
    }

    @Override
    public List<AssignmentStudentResponseDto>
    getAssignedStudents(
            Long assignmentId,
            Long teacherId
    ) {

        Assignment assignment =
                assignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new AssignmentNotFoundException(
                                        "Assignment not found with id: "
                                                + assignmentId
                                )
                        );

        if (!assignment.getTeacher()
                .getId()
                .equals(teacherId)) {

            throw new UnauthorizedException(
                    "You cannot view this assignment"
            );
        }

        return assignmentStudentRepository
                .findByAssignmentId(assignmentId)
                .stream()
                .map(entity ->
                        modelMapper.map(
                                entity,
                                AssignmentStudentResponseDto.class
                        )
                )
                .toList();
    }

    @Override
    public void removeStudent(
            Long assignmentStudentId,
            Long teacherId
    ) {

        AssignmentStudent mapping =
                assignmentStudentRepository
                        .findById(assignmentStudentId)
                        .orElseThrow(() ->
                                new AssignmentStudentNotFoundException(
                                        "Assignment student mapping not found"
                                )
                        );

        if (!mapping.getAssignment()
                .getTeacher()
                .getId()
                .equals(teacherId)) {

            throw new UnauthorizedException(
                    "You cannot remove this student"
            );
        }

        assignmentStudentRepository.delete(mapping);
    }
}