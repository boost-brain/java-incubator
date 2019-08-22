package boost.brain.course;

import boost.brain.course.email.SendEmail;
import org.apache.commons.codec.digest.DigestUtils;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Read javadoc in SendEmail class
 */

public class App 
{
    public static void main( String[] args ) throws IOException, MessagingException {
        //SendEmail.sendEmail();
        String s = "igor";

        System.out.println(DigestUtils.md5Hex(s));
        System.out.println(DigestUtils.md5Hex(s));
        //MD5Util.md5Custom(s);
    }
}
