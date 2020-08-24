package boost.brain.course.service.register;

import boost.brain.course.entity.EmailEntity;
import boost.brain.course.exceptions.EmailOrNameExistException;
import boost.brain.course.projects.repository.EmailRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class RegisterServiceImpl implements RegisterService {

//    @Value("${url.host}")
//    private String host;

//    @Value("${url.port.jsf-frontend}")
//    private String port;

    @Autowired
    EmailRepository emailRepository;

    @Override
    public EmailEntity addUserEmail(EmailEntity emailEntity) throws IOException, MessagingException {

        String email = emailEntity.getEmail();
        String name = emailEntity.getName();

        if ((emailRepository.findByEmail(email) != null) ||
                (emailRepository.findByName(name) != null)){
            throw new EmailOrNameExistException();
        }

//        SendEmail.sendEmail(emailEntity);
        String token = DigestUtils.md5Hex(email);
        emailEntity.setToken(token);
        return emailRepository.saveAndFlush(emailEntity);
    }

    @Override
    public void confirmation(EmailEntity emailEntity, String token) {
        try {
            emailRepository.findByToken(token);
            emailEntity.setConfirmed(true);
        }catch (Exception e){
            System.out.println("Email not found");
        }
    }

    @Override
    public void updateConfirmed(EmailEntity emailEntity) {
        EmailEntity repoEntity = emailRepository.findByToken(emailEntity.getToken());
        repoEntity.setConfirmed(true);
        emailRepository.saveAndFlush(repoEntity);
    }
}
