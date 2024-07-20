package students.x.studentsx.mapper;

import students.x.studentsx.dto.ProfessorDto;
import students.x.studentsx.entity.Professor;

public class ProfessorMapper {

    public static ProfessorDto mapToProfessorDto(Professor professor) {
        return new ProfessorDto(
                professor.getId(),
                UserMapper.mapToUserDto(professor.getUser()),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getPhoneNumber()

        );
    }

    public static Professor mapToProfessor(ProfessorDto professorDto) {
        return new Professor(
                professorDto.getId(),
                UserMapper.mapToUser(professorDto.getUser()),
                professorDto.getFirstName(),
                professorDto.getLastName(),
                professorDto.getPhoneNumber()
        );
    }
}
