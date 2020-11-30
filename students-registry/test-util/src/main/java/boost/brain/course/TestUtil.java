package boost.brain.course;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import boost.brain.course.common.auth.SessionCheck;
import lombok.extern.java.Log;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Log
public class TestUtil {
    public static void main(String[] args) {
        String credentialsUrl = "http://localhost:8081/api/credentials";
        String loginUrl = "http://localhost:8081/api/login";

        test(credentialsUrl, loginUrl, 10);
    }

    private static void test(String credentialsUrl, String loginUrl, int countCycles) {

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = restTemplateBuilder.build();

        Credentials credentials = new Credentials();
        HttpEntity<Credentials> credentialsRequest = new HttpEntity<>(credentials);
        String uuid = UUID.randomUUID().toString();

        for (int i = 0; i < countCycles; i++) {
            credentials.setLogin(uuid + i + "@testmail.ru");
            credentials.setPassword(credentials.getLogin());
            Boolean result;

            result = isCreate(credentialsUrl, restTemplate, credentialsRequest);
            if (result == null || !result) {
                log.severe("Error creating record");
                continue;
            }

            Session session = getNewSession(loginUrl, restTemplate, credentialsRequest);
            if (session == null || StringUtils.isEmpty(session.getSessionId())) {
                log.severe("Error login");
                continue;
            }

            result = isValidGetSessionCheck(loginUrl, restTemplate, session);
            if (result == null || !result) {
                log.severe("Error check");
                continue;
            }

            result = isLogout(loginUrl, restTemplate, session);
            if (result == null || !result) {
                log.severe("Error logout");
                continue;
            }

            result = isInvalidGetSessionCheck(loginUrl, restTemplate, session);
            if (result == null || !result) {
                log.severe("Error check after logout");
                continue;
            }


            try {
                testDelete(credentialsUrl, restTemplate, credentials);
            }catch (Exception e){
                log.severe(e.getLocalizedMessage());
            }

            log.info("Success cycle №" + (i + 1) + " of " + countCycles);
        }

        log.info("Finish!");
    }

    private static void testDelete(String credentialsUrl, RestTemplate restTemplate, Credentials credentials) {
        long startTime = System.nanoTime();
        restTemplate.delete(credentialsUrl + "/delete/" + credentials.getLogin());
        System.out.println("lag of delete = " + (System.nanoTime() - startTime) / 1000000);
    }

    private static Boolean isInvalidGetSessionCheck(String loginUrl, RestTemplate restTemplate, Session session) {
        Boolean result;
        long startTime = System.nanoTime();
        HttpHeaders headers = new HttpHeaders();
        headers.set("sessionId", session.getSessionId());
        try {
            ResponseEntity<SessionCheck> responseEntity = restTemplate.exchange(
                    loginUrl + "/check/",
                    HttpMethod.GET,
                    new HttpEntity(headers),
                    SessionCheck.class
            );
            result = false;
        } catch (Exception e) {
            result = true;
        }
        System.out.println("lag of isInvalidGetSessionCheck = " + (System.nanoTime() - startTime) / 1000000);
        return result;
    }

    private static Boolean isLogout(String loginUrl, RestTemplate restTemplate, Session session) {
        Boolean result;
        long startTime = System.nanoTime();
        result = restTemplate.getForObject(loginUrl + "/logout/" + session.getSessionId(), Boolean.class);
        System.out.println("lag of isLogout = " + (System.nanoTime() - startTime) / 1000000);
        return result;
    }

    private static Boolean isValidGetSessionCheck(String loginUrl, RestTemplate restTemplate, Session session) {
        long startTime = System.nanoTime();
        HttpHeaders headers = new HttpHeaders();
        headers.set("sessionId", session.getSessionId());
        ResponseEntity<SessionCheck> responseEntity = restTemplate.exchange(
                loginUrl + "/check/",
                HttpMethod.GET,
                new HttpEntity(headers),
                SessionCheck.class
        );
        SessionCheck sessionCheck = responseEntity.getBody();
        if (sessionCheck == null || StringUtils.isEmpty(sessionCheck.getEmail()) || sessionCheck.getRoles() == null) {
            return false;
        }
        System.out.println("lag of isValidGetSessionCheck = " + (System.nanoTime() - startTime) / 1000000);
        return true;
    }

    private static Session getNewSession(String loginUrl, RestTemplate restTemplate, HttpEntity<Credentials> credentialsRequest) {
        long startTime = System.nanoTime();
        Session session = restTemplate.postForObject(loginUrl + "/login", credentialsRequest, Session.class);
        System.out.println("lag of getNewSession = " + (System.nanoTime() - startTime) / 1000000);
        return session;
    }

    private static Boolean isCreate(String credentialsUrl, RestTemplate restTemplate, HttpEntity<Credentials> credentialsRequest) {
        Boolean result;
        long startTime = System.nanoTime();
        result = restTemplate.postForObject(credentialsUrl + "/create", credentialsRequest, Boolean.class);
        System.out.println("lag of isCreate = " + (System.nanoTime() - startTime) / 1000000);
        return result;
    }
}
