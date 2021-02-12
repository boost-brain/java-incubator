package boost.brain.course;

import boost.brain.course.common.auth.Credentials;
import boost.brain.course.common.auth.Session;
import boost.brain.course.common.auth.SessionCheck;
import boost.brain.course.common.auth.UserRole;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Data
@Log
public class TestAuthService {
    private String credentialsUrl;
    private String loginUrl;
    private String testAdminLogin;
    private String testAdminPwd;
    private RestTemplate restTemplate;
    private Credentials currentCredentials;
    private Credentials testAdminCredentials;
    private String uuid;
    private String testAdminSessionId;
    String currentSessionId;
    private String postfix;

    public TestAuthService(String credentialsUrl, String loginUrl, String testAdminLogin, String testAdminPwd) {
        this.credentialsUrl = credentialsUrl;
        this.loginUrl = loginUrl;
        this.testAdminLogin = testAdminLogin;
        this.testAdminPwd = testAdminPwd;
        this.postfix = "@testmail.ru";

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter());
        restTemplate = restTemplateBuilder.build();
        restTemplate.setErrorHandler(new MyErrorHandler());
        uuid = UUID.randomUUID().toString();
        currentCredentials = new Credentials();

        testAdminCredentials = new Credentials();
        testAdminCredentials.setLogin(testAdminLogin);
        testAdminCredentials.setPassword(testAdminPwd);
    }

    public void test(int countCycles) {

        this.loadTestSessionId();

        for (int i = 0; i < countCycles; i++) {

            currentCredentials.setLogin(uuid + i + postfix);
            currentCredentials.setPassword(currentCredentials.getLogin());

            this.testCreate();
            this.testLogin();
            this.testValidSession();
            this.testLogout();
            this.testInvalidSession();
            this.testDelete();

            log.info("Success cycle №" + (i + 1) + " of " + countCycles);
        }

        log.info("Finish!");
    }

    private void loadTestSessionId() {
        HttpEntity<Credentials> request = new HttpEntity<>(testAdminCredentials);
        Session testAdminSession = restTemplate.postForObject(loginUrl + "/login", request, Session.class);
        if (testAdminSession == null) {
            log.severe("testSession == null");
            throw new RuntimeException("testSession == null");
        }
        testAdminSessionId = testAdminSession.getSessionId();
    }

    private void testDelete() {
        long startTime = System.nanoTime();
        ResponseEntity<String> result = restTemplate.exchange(
                credentialsUrl + "/delete/" + currentCredentials.getLogin(),
                HttpMethod.DELETE,
                new HttpEntity<>(null, getAdminHeaders()),
                String.class);
        if (result.getStatusCodeValue() != 200) {
            log.severe("Error deleting record!");
            throw new RuntimeException("Error deleting record!");
        }
        System.out.println("lag of delete = " + (System.nanoTime() - startTime) / 1000000);
    }

    private void testInvalidSession() {


        long startTime = System.nanoTime();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                loginUrl + "/check",
                HttpMethod.POST,
                new HttpEntity<>(this.getCurrentHeaders()),
                String.class
        );
        if (responseEntity.getStatusCode() != HttpStatus.NOT_FOUND) {
            log.severe("Error testInvalidSession! StatusCode = " + responseEntity.getStatusCode());
            throw new RuntimeException("Error testInvalidSession! StatusCode = " + responseEntity.getStatusCode());
        }
        System.out.println("lag of testInvalidSession = " + (System.nanoTime() - startTime) / 1000000);
    }

    private void testLogout() {
        long startTime = System.nanoTime();
        Boolean result = restTemplate.postForObject(
                loginUrl + "/logout",
                new HttpEntity<>(null, this.getCurrentHeaders()),
                Boolean.class);
        if (!result) {
            log.severe("Error logout!");
            throw new RuntimeException("Error logout!");
        }
        System.out.println("lag of testLogout = " + (System.nanoTime() - startTime) / 1000000);

    }

    private void testValidSession() {
        long startTime = System.nanoTime();
        ResponseEntity<SessionCheck> responseEntity = restTemplate.exchange(
                loginUrl + "/check",
                HttpMethod.POST,
                new HttpEntity<>(this.getCurrentHeaders()),
                SessionCheck.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.severe("Error testValidSession! StatusCode = " + responseEntity.getStatusCode());
            throw new RuntimeException("Error testValidSession! StatusCode = " + responseEntity.getStatusCode());
        }

        SessionCheck sessionCheck = responseEntity.getBody();
        if (sessionCheck == null ||
                StringUtils.isEmpty(sessionCheck.getEmail()) ||
                sessionCheck.getRoles() == null ||
                !currentCredentials.getLogin().equals(sessionCheck.getEmail()) ||
                (sessionCheck.getRoles().size() != 1) ||
                !sessionCheck.getRoles().contains(UserRole.UNACTIVATED)) {
            log.severe("Error testValidSession!");
            throw new RuntimeException("Error testValidSession!");
        }

        System.out.println("lag of testValidSession = " + (System.nanoTime() - startTime) / 1000000);
    }

    private void testLogin() {
        long startTime = System.nanoTime();
        Session session = restTemplate.postForObject(
                loginUrl + "/login",
                new HttpEntity<>(currentCredentials),
                Session.class);
        if (session == null || StringUtils.isEmpty(session.getSessionId())) {
            log.severe("Error login!");
            throw new RuntimeException("Error login!");
        }
        currentSessionId = session.getSessionId();
        System.out.println("lag of testLogin = " + (System.nanoTime() - startTime) / 1000000);
    }

    private void testCreate() {
        long startTime = System.nanoTime();
        Boolean result = restTemplate.postForObject(
                credentialsUrl + "/create",
                new HttpEntity<>(currentCredentials, getAdminHeaders()),
                Boolean.class);
        if (!result) {
            log.severe("Error creating record!");
            throw new RuntimeException("Error creating record!");
        }
        System.out.println("lag of testCreate = " + (System.nanoTime() - startTime) / 1000000);
    }

    private HttpHeaders getAdminHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("sessionId", testAdminSessionId);
        return headers;
    }

    private HttpHeaders getCurrentHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("sessionId", currentSessionId);
        return headers;

    }

}