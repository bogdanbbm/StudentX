package students.x.studentsx.mapper;

import students.x.studentsx.dto.StudentGradeDto;
import students.x.studentsx.entity.StudentGrade;

public class StudentGradeMapper {
    public static StudentGradeDto mapToStudentGradeDto(StudentGrade studentGrade) {
        return new StudentGradeDto(
                studentGrade.getId(),
                StudentMapper.mapToStudentDto(studentGrade.getStudent()),
                studentGrade.getGrade()
        );
    }

    public static StudentGrade mapToStudentGrade(StudentGradeDto studentGradeDto) {
        return new StudentGrade(
                studentGradeDto.getId(),
                StudentMapper.mapToStudent(studentGradeDto.getStudent()),
                studentGradeDto.getGrade()
        );
    }
}
