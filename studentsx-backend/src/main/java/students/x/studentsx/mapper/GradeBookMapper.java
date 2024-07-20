package students.x.studentsx.mapper;

import students.x.studentsx.dto.GradeBookDto;
import students.x.studentsx.entity.GradeBook;

public class GradeBookMapper {

    public static GradeBookDto mapToGradeBookDto(GradeBook gradeBook) {

        return new GradeBookDto(
                gradeBook.getId(),
                LectureMapper.mapToLectureDto(gradeBook.getLecture()),
                StudentGradeMapper.mapToStudentGradeDto(gradeBook.getStudentGrade())
        );
    }

    public static GradeBook mapToGradeBook(GradeBookDto gradeBookDto) {
        return new GradeBook(
                gradeBookDto.getId(),
                LectureMapper.mapToLecture(gradeBookDto.getLecture()),
                StudentGradeMapper.mapToStudentGrade(gradeBookDto.getStudentGrade())
        );
    }
}