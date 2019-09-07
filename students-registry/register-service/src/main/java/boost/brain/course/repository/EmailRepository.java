package boost.brain.course.repository;

import boost.brain.course.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {

    EmailEntity findByToken(String token);
}
