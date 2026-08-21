package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.entity.Assignment;
import com.example.AssignmentTracker.entity.Teacher;
import com.example.AssignmentTracker.repository.AssignmentRepository;
import com.example.AssignmentTracker.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentService {
    
    private final AssignmentRepository assignmentRepository;
    private final TeacherRepository teacherRepository;

    public Assignment createAssignment(Assignment assignment, Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));
        assignment.setTeacher(teacher);
        return assignmentRepository.save(assignment);
    }

    public Assignment getAssignment(Long id) {
        return assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));
    }




    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }




    public Assignment updateAssignment(Long id, Assignment updatedAssignment) {
        Assignment as = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));
        as.setTitle(updatedAssignment.getTitle());
        as.setDescription(updatedAssignment.getDescription());
        as.setAssignedDate(updatedAssignment.getAssignedDate());
        as.setDueDate(updatedAssignment.getDueDate());
        return assignmentRepository.save(as);
    }



    public void deleteAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));
        assignmentRepository.delete(assignment);}
    
    
    
    
}
