package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.Exception.AssignmentNotFoundException;
import com.example.AssignmentTracker.Exception.TeacherNotFoundException;
import com.example.AssignmentTracker.Exception.UnauthorizedException;
import com.example.AssignmentTracker.dto.AssignmentRequestDto;
import com.example.AssignmentTracker.dto.AssignmentResponseDto;
import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.repository.AssignmentRepository;
import com.example.AssignmentTracker.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentServiceImpl implements  AssignmentService {
    
    private final AssignmentRepository assignmentRepository;
    private final TeacherRepository teacherRepository;
  private  final ModelMapper modelMapper;


    @Override
    public AssignmentResponseDto createAssignment(AssignmentRequestDto request, Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                        new TeacherNotFoundException(
                                "Teacher not found with id: " + teacherId
                        )
                );

        validateDates(
                request.getAssignedDate(),
                request.getDueDate()
        );

        Assignment assignment =
                modelMapper.map(request, Assignment.class);

        assignment.setTeacher(teacher);

        Assignment saved =
                assignmentRepository.save(assignment);

        AssignmentResponseDto response =
                modelMapper.map(
                        saved,
                        AssignmentResponseDto.class
                );

        response.setTeacherId(teacher.getId());
        response.setTeacherName(teacher.getName());

        return response;

    }

    @Override
    public AssignmentResponseDto getAssignment(Long assignmentId) {
        Assignment assignment =
                assignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new AssignmentNotFoundException(
                                        "Assignment not found with id: "
                                                + assignmentId
                                )
                        );

        return mapResponse(assignment);
    }

    @Override
    public List<AssignmentResponseDto> getTeacherAssignments(Long teacherId) {
        return assignmentRepository
                .findByTeacherId(teacherId)
                .stream()
                .map(this::mapResponse)
                .toList();
    }

    @Override
    public AssignmentResponseDto updateAssignment(Long assignmentId, AssignmentRequestDto request, Long teacherId) {
        Assignment assignment =
                assignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new AssignmentNotFoundException(
                                        "Assignment not found with id: "
                                                + assignmentId
                                )
                        );

        validateTeacherOwnership(
                assignment,
                teacherId
        );

        validateDates(
                request.getAssignedDate(),
                request.getDueDate()
        );

        modelMapper.map(request, assignment);

        Assignment updated =
                assignmentRepository.save(assignment);

        return mapResponse(updated);
    }

    @Override
    public void deleteAssignment(Long assignmentId, Long teacherId) {
        Assignment assignment =
                assignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new AssignmentNotFoundException(
                                        "Assignment not found with id: "
                                                + assignmentId
                                )
                        );

        validateTeacherOwnership(
                assignment,
                teacherId
        );

        assignmentRepository.delete(assignment);
    }

    private void validateTeacherOwnership(
            Assignment assignment,
            Long teacherId
    ) {

        if (!assignment.getTeacher()
                .getId()
                .equals(teacherId)) {

            throw new UnauthorizedException(
                    "You cannot modify another teacher's assignment"
            );
        }
    }

    private void validateDates(
            LocalDate assignedDate,
            LocalDate dueDate
    ) {

        if (dueDate.isBefore(assignedDate)) {

            throw new IllegalArgumentException(
                    "Due date cannot be before assigned date"
            );
        }
    }

    private AssignmentResponseDto mapResponse(
            Assignment assignment
    ) {

        AssignmentResponseDto response =
                modelMapper.map(
                        assignment,
                        AssignmentResponseDto.class
                );

        response.setTeacherId(
                assignment.getTeacher().getId()
        );

        response.setTeacherName(
                assignment.getTeacher().getName()
        );

        return response;
    }



}
