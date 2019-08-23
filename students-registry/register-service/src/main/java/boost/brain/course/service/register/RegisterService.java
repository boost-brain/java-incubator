package boost.brain.course.service.register;

import boost.brain.course.entity.EmailEntity;

public interface RegisterService {
    EmailEntity addUserEmail(EmailEntity emailEntity);
    void confirmation(EmailEntity entity, String token);
    void updateConfirmed(EmailEntity emailEntity);
}
