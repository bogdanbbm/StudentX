package students.x.studentsx.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import students.x.studentsx.dto.ProfessorDto;
import students.x.studentsx.entity.Professor;
import students.x.studentsx.entity.User;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.mapper.ProfessorMapper;
import students.x.studentsx.mapper.UserMapper;
import students.x.studentsx.repository.ProfessorRepository;
import students.x.studentsx.repository.UserRepository;
import students.x.studentsx.service.ProfessorService;
import students.x.studentsx.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final UserRepository userRepository;
    private ProfessorRepository professorRepository;

    @Override
    public ProfessorDto createProfessor(ProfessorDto professorDto) {
        Professor professor = ProfessorMapper.mapToProfessor(professorDto);

        User user = UserMapper.mapToUser(professorDto.getUser());

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException("User with username '" + user.getUsername() + "' already exists.", "409");
        }

        professor.setUser(user);
        Professor savedProfessor = professorRepository.save(professor);

        return ProfessorMapper.mapToProfessorDto(savedProfessor);
    }

    @Override
    public List<ProfessorDto> getProfessors() {
        List<Professor> professors = professorRepository.findAll();
        return professors.stream()
                .map(ProfessorMapper::mapToProfessorDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProfessorDto getProfessor(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new CustomException("Professor with id '" + id + "' not found.", "404"));
        return ProfessorMapper.mapToProfessorDto(professor);
    }

    @Override
    public ProfessorDto updateProfessor(Long id, ProfessorDto professorDto) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new CustomException("Professor with id '" + id + "' not found.", "404"));

        professor.setFirstName(professorDto.getFirstName());
        professor.setLastName(professorDto.getLastName());
        professor.setPhoneNumber(professorDto.getPhoneNumber());

        Professor updatedProfessor = professorRepository.save(professor);

        return ProfessorMapper.mapToProfessorDto(updatedProfessor);
    }

    @Override
    public void deleteProfessor(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new CustomException("Professor with id '" + id + "' not found.", "404"));
        professorRepository.delete(professor);
        userRepository.delete(professor.getUser());

    }

    @Override
    public ProfessorDto getProfessorByUserId(Long id) {
        List <Professor> professors = professorRepository.findAll();
        for (Professor student : professors) {
            if (student.getUser().getId().equals(id)) {
                return ProfessorMapper.mapToProfessorDto(student);
            }
        }
        throw new CustomException("Professor with user id '" + id + "' not found.", "404");
    }
}
