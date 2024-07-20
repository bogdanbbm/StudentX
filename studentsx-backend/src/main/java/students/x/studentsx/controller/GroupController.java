package students.x.studentsx.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import students.x.studentsx.dto.GroupDto;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.service.GroupService;
import students.x.studentsx.service.impl.JWTService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/groups")
@SecurityRequirement(name = "bearerAuth")@SecurityRequirement(name = "bearerAuth")
public class GroupController {
    private GroupService groupService;
    private JWTService jwtService;

    @GetMapping
    public ResponseEntity<?> getGroups(HttpServletRequest request) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            List<GroupDto> groups = groupService.getGroups();
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getGroup(HttpServletRequest request, @PathVariable("id") Long groupId) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            GroupDto groupDto = groupService.getGroup(groupId);
            return ResponseEntity.ok(groupDto);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @PostMapping
    public ResponseEntity<?> createGroup(HttpServletRequest request, @RequestBody GroupDto groupDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            GroupDto savedGroup = groupService.createGroup(groupDto);
            return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateGroup(HttpServletRequest request, @PathVariable("id") Long groupId, @RequestBody GroupDto groupDto) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            GroupDto updatedGroup = groupService.updateGroup(groupId, groupDto);
            return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteGroup(HttpServletRequest request, @PathVariable("id") Long groupId) {
        try {
            jwtService.verifyToken(request.getHeader("Authorization"));
            groupService.deleteGroup(groupId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(Integer.parseInt(e.getErrorCode())).body(e.getBody());
        }
    }

}
