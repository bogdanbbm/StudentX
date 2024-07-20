package students.x.studentsx.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import students.x.studentsx.dto.GradeBookDto;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.service.GradeBookService;
import students.x.studentsx.service.impl.JWTService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/gradeBook")
@SecurityRequirement(name = "bearerAuth")
public class GradeBookController {
    private GradeBookService gradeBookService;
    private JWTService jwtService;

    @GetMapping
    public ResponseEntity<?> getGradeBooks(HttpServletRequest request) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            List<GradeBookDto> grades = gradeBookService.getGrades();
            return new ResponseEntity<>(grades, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getGradeBook(HttpServletRequest request, @PathVariable("id") Long gradeId) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            GradeBookDto gradeDto = gradeBookService.getGrade(gradeId);
            return ResponseEntity.ok(gradeDto);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @PostMapping
    public ResponseEntity<?> createStudent(HttpServletRequest request, @RequestBody GradeBookDto gradeBookDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            GradeBookDto savedGrade = gradeBookService.createGrade(gradeBookDto);
            return new ResponseEntity<>(savedGrade, HttpStatus.CREATED);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateGrade(HttpServletRequest request, @PathVariable("id") Long gradeId, @RequestBody GradeBookDto gradeBookDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            GradeBookDto updatedGrade = gradeBookService.updateGrade(gradeId, gradeBookDto);
            return ResponseEntity.ok(updatedGrade);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteGrade(HttpServletRequest request, @PathVariable("id") Long gradeId) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            gradeBookService.deleteGrade(gradeId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }
}
