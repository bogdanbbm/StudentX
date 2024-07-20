package students.x.studentsx.service;

import students.x.studentsx.dto.StudentGradeDto;

import java.util.List;

public interface StudentGradeService {
    StudentGradeDto createStudentGrade(StudentGradeDto studentGradeDto);
    List<StudentGradeDto> getStudentGrades();
    StudentGradeDto getStudentGrade(Long id);
    StudentGradeDto updateStudentGrade(Long studentGradeId, StudentGradeDto studentGradeDto);
    void deleteStudentGrade(Long id);
}
