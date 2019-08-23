package boost.brain.course.service.register;

import boost.brain.course.entity.EmailEntity;
import boost.brain.course.repository.EmailRepository;
import boost.brain.course.service.register.RegisterService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    EmailRepository emailRepository;

    @Override
    public EmailEntity addUserEmail(EmailEntity emailEntity) {
        String email = emailEntity.getEmail();
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
