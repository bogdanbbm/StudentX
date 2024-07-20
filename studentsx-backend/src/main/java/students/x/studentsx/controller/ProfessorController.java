package students.x.studentsx.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import students.x.studentsx.dto.ProfessorDto;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.service.ProfessorService;
import students.x.studentsx.service.impl.JWTService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/professors")
@SecurityRequirement(name = "bearerAuth")
public class ProfessorController {

    private ProfessorService professorService;
    private JWTService jwtService;

    @GetMapping
    public ResponseEntity<?> getAllProfessors(HttpServletRequest request) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            List<ProfessorDto> professors = professorService.getProfessors();
            return new ResponseEntity<>(professors, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProfessorById(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            ProfessorDto professorDto = professorService.getProfessor(id);
            return ResponseEntity.ok(professorDto);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }
    
    @PostMapping
    public ResponseEntity<?> createProfessor(HttpServletRequest request, @RequestBody ProfessorDto professorDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            ProfessorDto savedProfessor = professorService.createProfessor(professorDto);
            return new ResponseEntity<>(savedProfessor, HttpStatus.CREATED);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProfessor(HttpServletRequest request, @PathVariable("id") Long id, @RequestBody ProfessorDto professorDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            ProfessorDto updatedProfessor = professorService.updateProfessor(id, professorDto);
            return ResponseEntity.ok(updatedProfessor);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProfessor(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            professorService.deleteProfessor(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }
}
