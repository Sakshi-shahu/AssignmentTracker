package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.dto.AdminRequest;
import com.example.AssignmentTracker.entity.*;

import java.util.List;

public interface AdminService {

    // Admin
    Admin createAdmin(AdminRequest request);

    List<Admin> getAllAdmins();

    Admin getAdminById(Long id);

    Admin updateAdmin(Long id, AdminRequest request);

    void deleteAdmin(Long id);


    // Trainer
    Teacher createTrainer(Teacher teacher);

    List<Teacher> getAllTrainers();

    Teacher getTrainerById(Long id);

    Teacher updateTrainer(Long id, Teacher teacher);

    void deleteTrainer(Long id);


    // Student
    Student createStudent(Student student, Long teacherId);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);


    // Assignment
    Assignment createAssignment(Assignment assignment, Long teacherId);

    List<Assignment> getAllAssignments();

    Assignment getAssignmentById(Long id);

    Assignment updateAssignment(Long id, Assignment assignment);

    void deleteAssignment(Long id);


    // Submissions
    List<AssignmentSubmission> getAllSubmissions();

    List<AssignmentSubmission> getSubmissionsByAssignment(Long assignmentId);
}
