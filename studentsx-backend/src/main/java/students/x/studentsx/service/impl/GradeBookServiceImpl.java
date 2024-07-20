package students.x.studentsx.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import students.x.studentsx.dto.GradeBookDto;
import students.x.studentsx.entity.GradeBook;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.exception.ResourceNotFoundException;
import students.x.studentsx.mapper.GradeBookMapper;
import students.x.studentsx.mapper.LectureMapper;
import students.x.studentsx.mapper.StudentGradeMapper;
import students.x.studentsx.mapper.StudentMapper;
import students.x.studentsx.repository.GradeBookRepository;
import students.x.studentsx.service.GradeBookService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeBookServiceImpl implements GradeBookService {

    private GradeBookRepository gradeBookRepository;

    @Override
    public GradeBookDto createGrade(GradeBookDto gradeBookDto) {
        GradeBook gradeBook = GradeBookMapper.mapToGradeBook(gradeBookDto);
        GradeBook savedGrade = gradeBookRepository.save(gradeBook);
        return GradeBookMapper.mapToGradeBookDto(savedGrade);
    }

    @Override
    public GradeBookDto updateGrade(Long id, GradeBookDto gradeBookDto) {
        GradeBook gradeBook = gradeBookRepository.findById(id)
                .orElseThrow(() -> new CustomException("GradeBook with id '" + id + "' not found.", "404"));

        gradeBook.setStudentGrade(StudentGradeMapper.mapToStudentGrade(gradeBookDto.getStudentGrade()));
        gradeBook.setLecture(LectureMapper.mapToLecture(gradeBookDto.getLecture()));

        return GradeBookMapper.mapToGradeBookDto(gradeBook);
    }

    @Override
    public List<GradeBookDto> getGrades() {
        List<GradeBook> grades = gradeBookRepository.findAll();
        return grades.stream()
                .map(GradeBookMapper::mapToGradeBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public GradeBookDto getGrade(Long id) {
        GradeBook gradeBook = gradeBookRepository.findById(id)
                .orElseThrow(() -> new CustomException("GradeBook with id '" + id + "' not found.", "404"));

        return GradeBookMapper.mapToGradeBookDto(gradeBook);
    }

    @Override
    public void deleteGrade(Long id) {
        GradeBook gradeBook = gradeBookRepository.findById(id)
                .orElseThrow(() -> new CustomException("GradeBook with id '" + id + "' not found.", "404"));
        gradeBookRepository.delete(gradeBook);
    }
}