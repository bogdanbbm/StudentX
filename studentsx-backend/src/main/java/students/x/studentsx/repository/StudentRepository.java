package students.x.studentsx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.x.studentsx.entity.Student;


public interface StudentRepository extends JpaRepository<Student, Long> {}