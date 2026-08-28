package com.example.AssignmentTracker.controller;


import com.example.AssignmentTracker.dto.*;
import com.example.AssignmentTracker.service.AssignmentService;
import com.example.AssignmentTracker.service.AssignmentStudentService;
import com.example.AssignmentTracker.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {


   // private  final TeacherService teacherService;

    private final AssignmentService assignmentService;

    private final AssignmentStudentService assignmentStudentService;

    private final SubmissionService submissionService;

//Create assignment
    @PostMapping("/{teacherId}/assignments")
    public ResponseEntity<AssignmentResponseDto>
    createAssignment(

            @PathVariable Long teacherId,

            @Valid
            @RequestBody AssignmentRequestDto request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        assignmentService.createAssignment(
                                request,
                                teacherId
                        )
                );
    }


//Get Teacher's assignments
    @GetMapping("/{teacherId}/assignments")
    public ResponseEntity<List<AssignmentResponseDto>>
    getTeacherAssignments(
            @PathVariable Long teacherId
    ) {

        return ResponseEntity.ok(
                assignmentService
                        .getTeacherAssignments(teacherId)
        );
    }



//Get single assignment

    @GetMapping("/assignments/{assignmentId}")
    public ResponseEntity<AssignmentResponseDto>
    getAssignment(
            @PathVariable Long assignmentId
    ) {

        return ResponseEntity.ok(
                assignmentService
                        .getAssignment(assignmentId)
        );
    }



    //update assignment

    @PutMapping("/{teacherId}/assignments/{assignmentId}")
    public ResponseEntity<AssignmentResponseDto>
    updateAssignment(

            @PathVariable Long teacherId,

            @PathVariable Long assignmentId,

            @Valid
            @RequestBody AssignmentRequestDto request
    ) {

        return ResponseEntity.ok(
                assignmentService.updateAssignment(
                        assignmentId,
                        request,
                        teacherId
                )
        );
    }


    // delete assignment


    @DeleteMapping("/{teacherId}/assignments/{assignmentId}")
    public ResponseEntity<Void>
    deleteAssignment(

            @PathVariable Long teacherId,

            @PathVariable Long assignmentId
    ) {

        assignmentService.deleteAssignment(
                assignmentId,
                teacherId
        );

        return ResponseEntity.noContent().build();
    }

//Assign students

    @PostMapping("/{teacherId}/assignments/{assignmentId}/students")
    public ResponseEntity<List<AssignmentStudentResponseDto>>
    assignStudents(

            @PathVariable Long teacherId,

            @PathVariable Long assignmentId,

            @Valid
            @RequestBody AssignmentStudentRequestDto request
    ) {

        return ResponseEntity.ok(
                assignmentStudentService.assignStudents(
                        assignmentId,
                        request.getStudentIds(),
                        teacherId
                )
        );
    }



//24. View assigned students



    @GetMapping("/{teacherId}/assignments/{assignmentId}/students")
    public ResponseEntity<List<AssignmentStudentResponseDto>>
    getAssignedStudents(

            @PathVariable Long teacherId,

            @PathVariable Long assignmentId
    ) {

        return ResponseEntity.ok(
                assignmentStudentService
                        .getAssignedStudents(
                                assignmentId,
                                teacherId
                        )
        );
    }

//  Remove student from assignment
    @DeleteMapping("/{teacherId}/assignment-students/{assignmentStudentId}")
    public ResponseEntity<Void>
    removeStudent(

            @PathVariable Long teacherId,

            @PathVariable Long assignmentStudentId
    ) {

        assignmentStudentService.removeStudent(
                assignmentStudentId,
                teacherId
        );

        return ResponseEntity.noContent().build();
    }



// 26. Teacher submissions

    @GetMapping("/{teacherId}/submissions")
    public ResponseEntity<List<SubmissionResponseDto>>
    getTeacherSubmissions(
            @PathVariable Long teacherId
    ) {

        return ResponseEntity.ok(
                submissionService
                        .getTeacherSubmissions(teacherId)
        );
    }


//27. Get single submission

    @GetMapping("/{teacherId}/submissions/{submissionId}")
    public ResponseEntity<SubmissionResponseDto>
    getSubmission(

            @PathVariable Long teacherId,

            @PathVariable Long submissionId
    ) {

        return ResponseEntity.ok(
                submissionService.getSubmission(
                        submissionId,
                        teacherId
                )
        );
    }


//28. Evaluate submission
@PutMapping("/{teacherId}/submissions/{submissionId}/evaluate")
public ResponseEntity<SubmissionResponseDto>
evaluateSubmission(

        @PathVariable Long teacherId,

        @PathVariable Long submissionId,

        @Valid
        @RequestBody
        EvaluateSubmissionRequestDto request
) {

    return ResponseEntity.ok(
            submissionService.evaluateSubmission(
                    submissionId,
                    request,
                    teacherId
            )
    );
}


}
