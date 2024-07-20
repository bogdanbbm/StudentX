package students.x.studentsx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.x.studentsx.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {}
