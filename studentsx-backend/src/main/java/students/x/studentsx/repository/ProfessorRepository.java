package students.x.studentsx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.x.studentsx.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {}