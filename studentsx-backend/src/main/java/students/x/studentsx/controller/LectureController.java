package students.x.studentsx.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import students.x.studentsx.dto.LectureDto;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.service.LectureService;
import students.x.studentsx.service.impl.JWTService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/lectures")
@SecurityRequirement(name = "bearerAuth")
public class LectureController {

    private LectureService lectureService;
    private JWTService jwtService;

    @GetMapping
    public ResponseEntity<?> getLectures(HttpServletRequest request) {
        try {
            List<LectureDto> lectures = lectureService.getLectures();
            return new ResponseEntity<>(lectures, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getLecture(HttpServletRequest request, Long id) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            LectureDto lecture = lectureService.getLecture(id);
            return ResponseEntity.ok(lecture);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @PostMapping
    public ResponseEntity<?> createLecture(HttpServletRequest request, @RequestBody LectureDto lectureDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            LectureDto savedLecture = lectureService.createLecture(lectureDto);
            return new ResponseEntity<>(savedLecture, HttpStatus.CREATED);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateLecture(HttpServletRequest request, @PathVariable Long id, @RequestBody LectureDto lectureDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            LectureDto updatedLecture = lectureService.updateLecture(id, lectureDto);
            return new ResponseEntity<>(updatedLecture, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteLecture(HttpServletRequest request, @PathVariable Long id) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            lectureService.deleteLecture(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }
}
