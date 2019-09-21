package boost.brain.course.email;

import boost.brain.course.entity.EmailEntity;
import org.apache.commons.codec.digest.DigestUtils;

public class EmailText {

    //FIXME Change host and port
    private static String host = "http://localhost";
    private static String port = "8080";
    private static String path = "/api/register";


    public static String emailText(EmailEntity emailEntity){

        String email = emailEntity.getEmail();
        String token = DigestUtils.md5Hex(email);
        String url =  host + ":" + port + path + "/verification_token/" + token;
        String message = "Для завершения регистрации, перейдите по ссылке: " + url;

        return message;
    }
}
