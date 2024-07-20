package students.x.studentsx.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import students.x.studentsx.dto.StudentGradeDto;
import students.x.studentsx.entity.StudentGrade;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.exception.ResourceNotFoundException;
import students.x.studentsx.mapper.StudentGradeMapper;
import students.x.studentsx.mapper.StudentMapper;
import students.x.studentsx.repository.StudentGradeRepository;
import students.x.studentsx.service.StudentGradeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentGradeServiceImpl implements StudentGradeService {

    public StudentGradeRepository studentGradeRepository;

    @Override
    public StudentGradeDto createStudentGrade(StudentGradeDto studentGradeDto) {
        StudentGrade studentGrade = StudentGradeMapper.mapToStudentGrade(studentGradeDto);
        StudentGrade savedStudentGrade = studentGradeRepository.save(studentGrade);
        return StudentGradeMapper.mapToStudentGradeDto(savedStudentGrade);
    }

    @Override
    public List<StudentGradeDto> getStudentGrades() {
        List<StudentGrade> studentGrades = studentGradeRepository.findAll();
        return studentGrades.stream()
                .map(StudentGradeMapper::mapToStudentGradeDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentGradeDto getStudentGrade(Long id) {
        StudentGrade studentGrade = studentGradeRepository.findById(id)
                .orElseThrow(() -> new CustomException("StudentGrade with id '" + id + "' not found.", "404"));
        return StudentGradeMapper.mapToStudentGradeDto(studentGrade);
    }

    @Override
    public StudentGradeDto updateStudentGrade(Long id, StudentGradeDto studentGradeDto) {
        StudentGrade studentGrade = studentGradeRepository.findById(id)
                .orElseThrow(() -> new CustomException("StudentGrade with id '" + id + "' not found.", "404"));

        studentGrade.setGrade(studentGradeDto.getGrade());
        studentGrade.setStudent(StudentMapper.mapToStudent(studentGradeDto.getStudent()));

        StudentGrade updatedStudentGrade = studentGradeRepository.save(studentGrade);

        return StudentGradeMapper.mapToStudentGradeDto(updatedStudentGrade);
    }

    @Override
    public void deleteStudentGrade(Long id) {
        StudentGrade student = studentGradeRepository.findById(id)
                .orElseThrow(() -> new CustomException("StudentGrade with id '" + id + "' not found.", "404"));
        studentGradeRepository.delete(student);
    }
}
