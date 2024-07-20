package students.x.studentsx.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import students.x.studentsx.dto.FeedbackDto;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.service.FeedbackService;
import students.x.studentsx.service.impl.JWTService;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/feedback")
@SecurityRequirement(name = "bearerAuth")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final JWTService jwtService;

    @GetMapping
    public ResponseEntity<?> getAllFeedback(HttpServletRequest request) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            List<FeedbackDto> feedbackList = feedbackService.getAllFeedback();
            return new ResponseEntity<>(feedbackList, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getFeedbackById(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            FeedbackDto feedbackDto = feedbackService.getFeedbackById(id);
            return ResponseEntity.ok(feedbackDto);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @PostMapping
    public ResponseEntity<?> addFeedback(HttpServletRequest request, @RequestBody FeedbackDto feedbackDto) {
        try {
            FeedbackDto savedFeedback = feedbackService.addFeedback(feedbackDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFeedback);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFeedbackById(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            FeedbackDto deletedFeedback = feedbackService.deleteFeedbackById(id);
            return ResponseEntity.ok(deletedFeedback);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }
}
