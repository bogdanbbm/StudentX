package students.x.studentsx.service;

import students.x.studentsx.dto.ProfessorDto;

import java.util.List;

public interface ProfessorService {
    ProfessorDto createProfessor(ProfessorDto professorDto);
    List<ProfessorDto> getProfessors();
    ProfessorDto getProfessor(Long id);
    ProfessorDto updateProfessor(Long id, ProfessorDto professorDto);
    void deleteProfessor(Long id);
    ProfessorDto getProfessorByUserId(Long id);
}
