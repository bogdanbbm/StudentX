package students.x.studentsx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.x.studentsx.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {}