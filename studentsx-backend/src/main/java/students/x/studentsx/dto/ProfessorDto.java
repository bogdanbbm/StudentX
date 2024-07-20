package students.x.studentsx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDto {
    private Long id;
    private UserDto user;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
