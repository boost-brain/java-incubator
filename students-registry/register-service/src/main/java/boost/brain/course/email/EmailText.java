package boost.brain.course.email;

import boost.brain.course.entity.EmailEntity;
import org.apache.commons.codec.digest.DigestUtils;

public class EmailText {


    public static String emailText(EmailEntity emailEntity){
        String email = emailEntity.getEmail();
        String token = DigestUtils.md5Hex(email);
        return "Для завершения регистрации, перейдите по ссылке: " +
                "http://localhost:8080/verification_token/" + token;
    }
}
