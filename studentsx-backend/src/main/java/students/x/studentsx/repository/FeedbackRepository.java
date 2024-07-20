package students.x.studentsx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.x.studentsx.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {}
