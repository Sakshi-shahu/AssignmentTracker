package com.example.AssignmentTracker.service;

import com.example.AssignmentTracker.dto.AdminRequestDto;
import com.example.AssignmentTracker.dto.AdminResponseDto;
import com.example.AssignmentTracker.Exception.AdminNotFoundException;
import com.example.AssignmentTracker.Exception.AssignmentNotFoundException;
import com.example.AssignmentTracker.Exception.StudentNotFoundException;
import com.example.AssignmentTracker.Exception.TeacherNotFoundException;
import com.example.AssignmentTracker.entity.*;
import com.example.AssignmentTracker.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;


    // ================= ADMIN =================

    @Override
    public AdminResponseDto createAdmin(AdminRequestDto request) {

        Admin admin = new Admin();

        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        admin.setPhone(request.getPhone());
        admin.setActive(request.getActive());

        Admin savedAdmin = adminRepository.save(admin);

        return mapToResponse(savedAdmin);
    }


    @Override
    public List<AdminResponseDto> getAllAdmins() {

        return adminRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    public AdminResponseDto getAdminById(Long id) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new AdminNotFoundException(
                                "Admin not found with id: " + id));

        return mapToResponse(admin);
    }

    @Override
    public AdminResponseDto updateAdmin(Long id, AdminRequestDto request) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new AdminNotFoundException(
                                "Admin not found with id: " + id));

        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        admin.setPhone(request.getPhone());
        admin.setActive(request.getActive());

        Admin updatedAdmin = adminRepository.save(admin);

        return mapToResponse(updatedAdmin);
    }





    @Override
    public void deleteAdmin(Long id) {

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() ->
                        new AdminNotFoundException(
                                "Admin not found with id: " + id));

        adminRepository.delete(admin);
    }


    // ================= ADMIN RESPONSE MAPPING =================

    private AdminResponseDto mapToResponse(Admin admin) {

        return new AdminResponseDto(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhone(),
                admin.getActive()
        );
    }


    // ================= TRAINER =================

    @Override
    public Teacher createTrainer(Long adminId, Teacher teacher) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new AdminNotFoundException(
                                "Admin not found with id: " + adminId));

        teacher.setCreatedByAdmin(admin);

        return teacherRepository.save(teacher);
    }


    @Override
    public List<Teacher> getAllTrainers() {

        return teacherRepository.findAll();
    }


    @Override
    public Teacher getTrainerById(Long id) {

        return teacherRepository.findById(id)
                .orElseThrow(() ->
                        new TeacherNotFoundException(
                                "Teacher not found with id: " + id));
    }


    @Override
    public Teacher updateTrainer(Long id, Teacher teacher) {

        Teacher existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new TeacherNotFoundException(
                                "Teacher not found with id: " + id));

        existingTeacher.setName(teacher.getName());
        existingTeacher.setSubject(teacher.getSubject());

        return teacherRepository.save(existingTeacher);
    }


    @Override
    public void deleteTrainer(Long id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new TeacherNotFoundException(
                                "Teacher not found with id: " + id));

        teacherRepository.delete(teacher);
    }


    // ================= STUDENT =================

    @Override
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    @Override
    public Student createStudent(Student student, Long teacherId) {

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                        new TeacherNotFoundException(
                                "Teacher not found with id: " + teacherId));

        student.setTeacher(teacher);

        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id));
    }


    @Override
    public Student updateStudent(Long id, Student student) {

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id));

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setCourse(student.getCourse());

        return studentRepository.save(existingStudent);
    }


    @Override
    public void deleteStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id));

        studentRepository.delete(student);
    }


    // ================= ASSIGNMENT =================

    @Override
    public Assignment createAssignment(
            Assignment assignment,
            Long teacherId) {

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                        new TeacherNotFoundException(
                                "Teacher not found with id: " + teacherId));

        assignment.setTeacher(teacher);

        return assignmentRepository.save(assignment);
    }


    @Override
    public List<Assignment> getAllAssignments() {

        return assignmentRepository.findAll();
    }


    @Override
    public Assignment getAssignmentById(Long id) {

        return assignmentRepository.findById(id)
                .orElseThrow(() ->
                        new AssignmentNotFoundException(
                                "Assignment not found with id: " + id));
    }


    @Override
    public Assignment updateAssignment(
            Long id,
            Assignment assignment) {

        Assignment existingAssignment =
                assignmentRepository.findById(id)
                        .orElseThrow(() ->
                                new AssignmentNotFoundException(
                                        "Assignment not found with id: " + id));

        existingAssignment.setTitle(assignment.getTitle());
        existingAssignment.setDescription(assignment.getDescription());
        existingAssignment.setAssignedDate(assignment.getAssignedDate());
        existingAssignment.setDueDate(assignment.getDueDate());

        return assignmentRepository.save(existingAssignment);
    }


    @Override
    public void deleteAssignment(Long id) {

        Assignment assignment =
                assignmentRepository.findById(id)
                        .orElseThrow(() ->
                                new AssignmentNotFoundException(
                                        "Assignment not found with id: " + id));

        assignmentRepository.delete(assignment);
    }


    // ================= SUBMISSIONS =================

    @Override
    public List<AssignmentSubmission> getAllSubmissions() {

        return submissionRepository.findAll();
    }


    @Override
    public List<AssignmentSubmission> getSubmissionsByAssignment(
            Long assignmentId) {

        assignmentRepository.findById(assignmentId)
                .orElseThrow(() ->
                        new AssignmentNotFoundException(
                                "Assignment not found with id: " + assignmentId));

        return submissionRepository.findAll()
                .stream()
                .filter(submission ->
                        submission.getAssignment()
                                .getId()
                                .equals(assignmentId))
                .toList();
    }


    // ================= ASSIGN ASSIGNMENT =================

    @Override
    public void assignAssignmentToStudent(
            Long assignmentId,
            Long studentId) {

        Assignment assignment =
                assignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new AssignmentNotFoundException(
                                        "Assignment not found with id: "
                                                + assignmentId));

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        "Student not found with id: "
                                                + studentId));

        // AssignmentStudent mapping will be handled
        // according to your current entity structure.
    }
}