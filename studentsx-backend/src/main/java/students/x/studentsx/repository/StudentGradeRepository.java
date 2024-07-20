package students.x.studentsx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.x.studentsx.entity.StudentGrade;

public interface StudentGradeRepository extends JpaRepository<StudentGrade, Long> {}