package students.x.studentsx.service;

import students.x.studentsx.dto.GradeBookDto;

import java.util.List;

public interface GradeBookService {
    GradeBookDto createGrade(GradeBookDto gradeBookDto);
    GradeBookDto updateGrade(Long id, GradeBookDto gradeBookDto);
    List<GradeBookDto> getGrades();
    GradeBookDto getGrade(Long id);
    void deleteGrade(Long id);
}
