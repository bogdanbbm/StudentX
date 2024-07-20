package students.x.studentsx.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import students.x.studentsx.dto.LectureGroupDto;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.service.LectureGroupService;
import students.x.studentsx.service.impl.JWTService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/lectureGroups")
@SecurityRequirement(name = "bearerAuth")
public class LectureGroupController {

    private LectureGroupService lectureGroupService;
    private JWTService jwtService;

    @GetMapping
    public ResponseEntity<?> getLectureGroups(HttpServletRequest request) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            List<LectureGroupDto> lectureGroups = lectureGroupService.getLectureGroups();
            return new ResponseEntity<>(lectureGroups, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getLectureGroup(HttpServletRequest request, @PathVariable("id") Long lectureGroupId) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            LectureGroupDto lectureGroupDto = lectureGroupService.getLectureGroup(lectureGroupId);
            return ResponseEntity.ok(lectureGroupDto);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @PostMapping
    public ResponseEntity<?> createLectureGroup(HttpServletRequest request, @RequestBody LectureGroupDto lectureGroupDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            LectureGroupDto savedLectureGroup = lectureGroupService.createLectureGroup(lectureGroupDto);
            return new ResponseEntity<>(savedLectureGroup, HttpStatus.CREATED);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateLectureGroup(HttpServletRequest request, @PathVariable("id") Long lectureGroupId, @RequestBody LectureGroupDto lectureGroupDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            LectureGroupDto updatedLectureGroup = lectureGroupService.updateLectureGroup(lectureGroupId, lectureGroupDto);
            return ResponseEntity.ok(updatedLectureGroup);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteLectureGroup(HttpServletRequest request, @PathVariable("id") Long lectureGroupId) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            lectureGroupService.deleteLectureGroup(lectureGroupId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }
    
}
