package students.x.studentsx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.x.studentsx.entity.LectureGroup;


public interface LectureGroupRepository extends JpaRepository<LectureGroup, Long> {}