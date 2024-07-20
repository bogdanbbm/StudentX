package students.x.studentsx.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import students.x.studentsx.dto.StudentDto;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.service.StudentService;
import students.x.studentsx.service.impl.JWTService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/students")
@SecurityRequirement(name = "bearerAuth")
public class StudentController {

    private StudentService studentService;
    private JWTService jwtService;

    @GetMapping
    public ResponseEntity<?> getAllStudents(HttpServletRequest request) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            List<StudentDto> students = studentService.getStudents();
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getStudentById(HttpServletRequest request, @PathVariable("id") Long studentId) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            StudentDto studentDto = studentService.getStudent(studentId);
            return ResponseEntity.ok(studentDto);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @PostMapping
    public ResponseEntity<?> createStudent(HttpServletRequest request, @RequestBody StudentDto studentDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            StudentDto savedStudent = studentService.createStudent(studentDto);
            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateStudent(HttpServletRequest request, @PathVariable("id") Long studentId, @RequestBody StudentDto studentDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            StudentDto updatedStudent = studentService.updateStudent(studentId, studentDto);
            return ResponseEntity.ok(updatedStudent);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudent(HttpServletRequest request, @PathVariable("id") Long studentId) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            studentService.deleteStudent(studentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }
}
