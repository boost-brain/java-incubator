package boost.brain.course.service.register;

import boost.brain.course.entity.EmailEntity;

import javax.mail.MessagingException;
import java.io.IOException;

public interface RegisterService {

    EmailEntity addUserEmail(EmailEntity emailEntity) throws IOException, MessagingException;
    void confirmation(EmailEntity emailEntity, String token);
    void updateConfirmed(EmailEntity emailEntity);
}
