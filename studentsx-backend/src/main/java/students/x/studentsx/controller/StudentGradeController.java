package students.x.studentsx.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import students.x.studentsx.dto.StudentGradeDto;
import students.x.studentsx.dto.UserDto;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.service.StudentGradeService;
import students.x.studentsx.service.impl.JWTService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/studentGrades")
@SecurityRequirement(name = "bearerAuth")
public class StudentGradeController {

    private StudentGradeService studentGradeService;
    private JWTService jwtService;

    @GetMapping
    public ResponseEntity<?> getAllStudents(HttpServletRequest request) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            List<StudentGradeDto> studentGrades = studentGradeService.getStudentGrades();
            return new ResponseEntity<>(studentGrades, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getStudentGradeById(HttpServletRequest request, @PathVariable long id) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            StudentGradeDto studentGrade = studentGradeService.getStudentGrade(id);
            return new ResponseEntity<>(studentGrade, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @PostMapping
    public ResponseEntity<?> createStudnetGrade(HttpServletRequest request, @RequestBody StudentGradeDto studentGradeDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            StudentGradeDto savedStudentGrade = studentGradeService.createStudentGrade(studentGradeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStudentGrade);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateStudentGrade(HttpServletRequest request, @PathVariable long id, @RequestBody StudentGradeDto studentGradeDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            StudentGradeDto studentGrade = studentGradeService.updateStudentGrade(id, studentGradeDto);
            return new ResponseEntity<>(studentGrade, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudentGrade(HttpServletRequest request, @PathVariable long id) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            studentGradeService.deleteStudentGrade(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

}
