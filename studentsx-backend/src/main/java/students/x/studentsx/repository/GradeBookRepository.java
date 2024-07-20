package students.x.studentsx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.x.studentsx.entity.GradeBook;


public interface GradeBookRepository extends JpaRepository<GradeBook, Long> {}