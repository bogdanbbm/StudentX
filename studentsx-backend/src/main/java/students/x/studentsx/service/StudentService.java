package students.x.studentsx.service;

import students.x.studentsx.dto.StudentDto;

import java.util.List;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDto);
    List<StudentDto> getStudents();
    StudentDto getStudent(Long id);
    StudentDto updateStudent(Long studentId, StudentDto studentDto);
    void deleteStudent(Long id);
    StudentDto getStudentByUserId(Long id);
}
