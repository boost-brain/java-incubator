package boost.brain.course.projects.repository;

import boost.brain.course.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {

    EmailEntity findByToken(String token);
    EmailEntity findByEmail(String email);
    EmailEntity findByName (String name);
}
