package boost.brain.course.email;

import boost.brain.course.entity.EmailEntity;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * You should download javax.mail for correct work of program
 * and add path to the jar-file: "File -> Project Structure -> SDKs -> classpath -> "+" button"
 * (in Intellij Idea)
 */

public class SendEmail extends EmailText implements Email {

    public static void sendEmail(EmailEntity emailEntity) throws IOException, MessagingException {

        final Properties properties = new Properties();
        properties.load(boost.brain.course.email.SendEmail.class.getClassLoader()
                .getResourceAsStream("mail.properties"));

        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(mailSession);

        message.setFrom(new InternetAddress(mainEmailAccount));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(/*emailEntity.getEmail()*/"igorvasylevskyi@gmail.com"));
        message.setSubject(subject);
        message.setText(emailText(emailEntity));

        Transport tr = mailSession.getTransport();
        tr.connect(null, mainEmailPassword);
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();

    }
}
