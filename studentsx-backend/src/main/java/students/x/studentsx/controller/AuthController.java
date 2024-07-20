package students.x.studentsx.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import students.x.studentsx.dto.ProfessorDto;
import students.x.studentsx.dto.StudentDto;
import students.x.studentsx.dto.UserDto;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.service.ProfessorService;
import students.x.studentsx.service.StudentService;
import students.x.studentsx.service.UserService;
import students.x.studentsx.service.impl.JWTService;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/login")
public class AuthController {

    private UserService userService;
    private StudentService studentService;
    private ProfessorService professorService;
    private JWTService jwtService;

    @PostMapping
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        try {
            UserDto user = userService.getUserByUsername(userDto.getUsername());

            if (!user.getPassword().equals(userDto.getPassword())) {
                Map<String, String> body = new HashMap<>();

                body.put("errorCode", "401");
                body.put("message", "Invalid password for user '" + userDto.getUsername() + "'");

                return ResponseEntity.status(401).body(body);
            }

            Map<String, String> body = new HashMap<>();

            if (user.getRole().equals("STUDENT")) {
                StudentDto student = studentService.getStudentByUserId(user.getId());
                String token = jwtService.generateToken(student.getUser());

                body.put("token", token);
                return ResponseEntity.status(201).body(body);
            }

            if (user.getRole().equals("TEACHER")) {
                ProfessorDto professor = professorService.getProfessorByUserId(user.getId());
                String token = jwtService.generateToken(professor.getUser());

                body.put("token", token);
                return ResponseEntity.status(201).body(body);
            }

            if (user.getRole().equals("ADMIN")) {
                String token = jwtService.generateToken(user);

                body.put("token", token);
                return ResponseEntity.status(201).body(body);
            }

            body.put("errorCode", "500");
            body.put("message", "Account didn't match the settings of platform.");

            return ResponseEntity.status(500).body(body);

        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }
}
