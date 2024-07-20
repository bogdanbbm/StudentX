package students.x.studentsx.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import students.x.studentsx.dto.UserDto;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.service.UserService;
import org.springframework.http.ResponseEntity;
import students.x.studentsx.service.impl.JWTService;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    
    private UserService userService;
    private JWTService jwtService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(HttpServletRequest request) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            List<UserDto> users = userService.getUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            UserDto userDto = userService.getUser(id);
            return ResponseEntity.ok(userDto);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(HttpServletRequest request, @RequestBody UserDto userDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            UserDto savedUser = userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(HttpServletRequest request, @PathVariable("id") Long studentId, @RequestBody UserDto studentDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            UserDto updatedStudent = userService.updateUser(studentId, studentDto);
            return ResponseEntity.ok(updatedStudent);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(HttpServletRequest request, @PathVariable("id") Long studentId) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            userService.deleteUser(studentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }


}
