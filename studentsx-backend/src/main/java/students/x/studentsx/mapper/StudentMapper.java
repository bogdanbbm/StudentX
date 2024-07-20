package students.x.studentsx.mapper;

import students.x.studentsx.dto.StudentDto;
import students.x.studentsx.entity.Student;

public class StudentMapper {

    public static StudentDto mapToStudentDto(Student student) {

        return new StudentDto(
                student.getId(),
                UserMapper.mapToUserDto(student.getUser()),
                student.getFirstName(),
                student.getLastName(),
                student.getFatherInitial(),
                student.getCountry(),
                student.getAge(),
                student.getPhoneNumber(),
                GroupMapper.mapToGroupDto(student.getGroup())
        );
    }

    public static Student mapToStudent(StudentDto studentDto) {
        return new Student(
                studentDto.getId(),
                UserMapper.mapToUser(studentDto.getUser()),
                studentDto.getFirstName(),
                studentDto.getLastName(),
                studentDto.getFatherInitial(),
                studentDto.getCountry(),
                studentDto.getAge(),
                studentDto.getPhoneNumber(),
                GroupMapper.mapToGroup(studentDto.getGroup())
        );
    }
}
