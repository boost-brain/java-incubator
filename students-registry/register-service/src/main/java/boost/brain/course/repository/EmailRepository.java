package boost.brain.course.repository;

import boost.brain.course.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {

    EmailEntity findByToken(String token);
    EmailEntity findByEmail(String email);
    EmailEntity findByName (String name);
}
