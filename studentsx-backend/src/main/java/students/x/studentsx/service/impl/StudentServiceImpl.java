package students.x.studentsx.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import students.x.studentsx.dto.StudentDto;
import students.x.studentsx.entity.Student;
import students.x.studentsx.entity.User;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.exception.ResourceNotFoundException;
import students.x.studentsx.mapper.GroupMapper;
import students.x.studentsx.mapper.StudentMapper;
import students.x.studentsx.mapper.UserMapper;
import students.x.studentsx.repository.GroupRepository;
import students.x.studentsx.repository.StudentRepository;
import students.x.studentsx.repository.UserRepository;
import students.x.studentsx.service.GroupService;
import students.x.studentsx.service.StudentService;
import students.x.studentsx.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private GroupService groupService;
    private StudentRepository studentRepository;
    private UserRepository userRepository;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = StudentMapper.mapToStudent(studentDto);

        User user = UserMapper.mapToUser(studentDto.getUser());

        groupService.getGroup(student.getGroup().getId());

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException("User with username '" + user.getUsername() + "' already exists.", "409");
        }

        student.setUser(user);
        Student savedStudent = studentRepository.save(student);

        return StudentMapper.mapToStudentDto(savedStudent);
    }

    @Override
    public List<StudentDto> getStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(StudentMapper::mapToStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Student with id '" + id + "' not found.", "404"));
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto updatedStudentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Student with id '" + id + "' not found.", "404"));

        student.setFirstName(updatedStudentDto.getFirstName());
        student.setLastName(updatedStudentDto.getLastName());
        student.setAge(updatedStudentDto.getAge());
        student.setCountry(updatedStudentDto.getCountry());
        student.setFatherInitial(updatedStudentDto.getFatherInitial());
        student.setPhoneNumber(updatedStudentDto.getPhoneNumber());
        student.setGroup(GroupMapper.mapToGroup(updatedStudentDto.getGroup()));

        Student updatedStudent = studentRepository.save(student);

        return StudentMapper.mapToStudentDto(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Student with id '" + id + "' not found.", "404"));
        studentRepository.delete(student);
        userRepository.delete(student.getUser());
    }

    @Override
    public StudentDto getStudentByUserId(Long id) {
        List <Student> students = studentRepository.findAll();
        for (Student student : students) {
            if (student.getUser().getId().equals(id)) {
                return StudentMapper.mapToStudentDto(student);
            }
        }
        throw new CustomException("Student with user id '" + id + "' not found.", "404");
    }
}
