package students.x.studentsx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.x.studentsx.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {}