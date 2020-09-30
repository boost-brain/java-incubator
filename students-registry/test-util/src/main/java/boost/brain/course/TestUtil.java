package boost.brain.course;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * Hello world!
 */
@Log
public class TestUtil {

    public static void main(String[] args) {
        String credentialsUrl = "http://localhost:8081/api/credentials";
        String loginUrl = "http://localhost:8081/api/login";

        RestTemplate restTemplate = new RestTemplate();

        Credentials credentials = new Credentials();
        HttpEntity<Credentials> credentialsRequest = new HttpEntity<>(credentials);

        String xPwd = UUID.randomUUID().toString() ;
        String xEmail = "_" + xPwd + "@test.ru";

        long startTestTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {

            long startTime = System.currentTimeMillis();

            credentials.setLogin("test_email_" + i + xEmail);
            credentials.setPassword(xPwd);

            long startCreateTime = System.currentTimeMillis();
            Boolean resultCreate = restTemplate.postForObject(credentialsUrl + "/create", credentialsRequest, Boolean.class);

            if (resultCreate == null || !resultCreate) {
                log.severe("Error creating record");
                continue;
            }
            log.info("Success create " + (System.currentTimeMillis() - startCreateTime) + " ms");

            long startLoginTime = System.currentTimeMillis();
            Session session = restTemplate.postForObject(loginUrl + "/login", credentialsRequest, Session.class);
            if (session == null || StringUtils.isEmpty(session.getSessionId())) {
                log.severe("Error login");
                continue;
            }
            log.info("Success login " + (System.currentTimeMillis() - startLoginTime) + " ms");


            long startCheckValidSessionTime = System.currentTimeMillis();
            Boolean resultCheckValidSession = restTemplate.getForObject(loginUrl + "/check/" + session.getSessionId(), Boolean.class);
            if (resultCheckValidSession == null || !resultCheckValidSession) {
                log.severe("Error check");
                continue;
            }
            log.info("Success check valid session " + (System.currentTimeMillis() - startCheckValidSessionTime) + " ms");



            long startLogoutTime = System.currentTimeMillis();
            Boolean resultLogout = restTemplate.getForObject(loginUrl + "/logout/" + session.getSessionId(), Boolean.class);
            if (resultLogout == null || !resultLogout) {
                log.severe("Error logout");
                continue;
            }
            log.info("Success logout " + (System.currentTimeMillis() - startLogoutTime) + " ms");



            long startCheckInvalidSessionTime = System.currentTimeMillis();
            Boolean resultCheckInvalidSession = restTemplate.getForObject(loginUrl + "/check/" + session.getSessionId(), Boolean.class);
            if (resultCheckInvalidSession == null || resultCheckInvalidSession) {
                log.severe("Error check after logout");
                continue;
            }
            log.info("Success check invalid session " + (System.currentTimeMillis() - startCheckInvalidSessionTime) + " ms");


            long startDeleteTime = System.currentTimeMillis();
            try {
                restTemplate.delete(credentialsUrl + "/delete/" + credentials.getLogin());
            }catch (Exception e){
                log.severe(e.getLocalizedMessage());
            }
            log.info("Success delete " + (System.currentTimeMillis() - startDeleteTime) + " ms");



            log.info("Success " + (System.currentTimeMillis() - startTime) + " ms");
            System.out.println();

        }

        log.info("Success Tests  " + (System.currentTimeMillis() - startTestTime) + " ms");
    }
}
